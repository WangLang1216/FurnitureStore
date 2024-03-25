package com.summer.commonmodule.utils;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.text.StrPool;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    /**
     * 默认桶名
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 判断bucket是否存在
     * @param bucketName
     */
    @SneakyThrows
    public boolean existBucket(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储bucket
     * @param bucketName
     * @return boolean
     */
    public boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *  删除存储bucket
     * @param bucketName
     * @return boolean
     */
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建文件路径
     * @param fileName 原始文件名称
     * @return FilePath
     */
    public String createFilePath(String fileName) {
        return fileName;
    }

    /**
     * 根据文件路径获取文件名称
     * @param filePath 文件路径
     * @return 文件名
     */
    public String getFileNameByPath(String filePath) {
        String[] split = StringUtils.split(filePath, "/");
        return split[split.length - 1];
    }

    /**
     * 查看桶内文件信息
     * @param bucketName 可为空
     * @return List
     */
    @SneakyThrows
    public List<Map<String, String>> queryFileListInBucket(String bucketName) {
        // 桶选择
        String thisBucketName = bucketName == null || bucketName.equals("") ? this.bucketName : bucketName;
        List<Map<String, String>> objectItemList = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(thisBucketName).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            // 不为目录时添加，如果为目录，需递归，暂不实现
            if (!item.isDir()) {
                Map<String, String> map = new HashMap<>();
                map.put("objectName", item.objectName());
                map.put("size", String.valueOf(item.size()));
                objectItemList.add(map);
            }
        }
        return objectItemList;
    }

    /**
     * 判断桶内是否包含该文件
     * @param fileName
     * @param bucketName 可为空
     * @return boolean
     */
    public boolean isContainInBucketByFile(String fileName, String bucketName) {
        List<Map<String, String>> objectItemList = queryFileListInBucket(bucketName);
        for (Map<String, String> item : objectItemList) {
            if (item.get("objectName").equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 单文件上传
     * @param file
     * @param bucketName 可为空
     * @return String
     */
    @SneakyThrows
    public String upload(MultipartFile file, String bucketName) {
        // 桶名
        String thisBucketName = bucketName == null || bucketName.equals("") ? this.bucketName : bucketName;
        // 文件名
        String fileName = file.getOriginalFilename();
        // 文件流
        InputStream inputStream = file.getInputStream();
        // 文件大小
        long size = file.getSize();
        // 文件路径
        String filePath = createFilePath(fileName);
        // 存储到Minio
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(thisBucketName)
                .object(filePath)
                .stream(inputStream, size, -1)
                .contentType(file.getContentType())
                .build());
            return filePath;
    }

    /**
     * 批量文件上传
     * @param file 数组
     * @param bucketName 可为空
     * @return Map key-文件名 value-文件路径
     */
    @SneakyThrows
    public List<Map<String, String>> upload(MultipartFile[] file, String bucketName) {
        List<Map<String, String>> filePaths = new ArrayList<>();
        String thisBucketName = bucketName == null || bucketName.equals("") ? this.bucketName : bucketName;
        for (MultipartFile item : file) {
            String fileName = item.getOriginalFilename();
            InputStream inputStream = item.getInputStream();
            long size = item.getSize();
            String filePath = createFilePath(fileName);
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("filePath", bucketName + StrPool.SLASH + filePath);
            filePaths.add(map);
            System.out.println(fileName + "的文件路径为：" + filePath);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(thisBucketName)
                    .object(filePath)
                    .stream(inputStream, size, -1)
                    .contentType(item.getContentType())
                    .build());
        }
        return filePaths;
    }

    /**
     * 单文件下载（因为存在多级目录，通过文件路径）
     * @param response
     * @param request
     * @param filePath
     */
    @SneakyThrows
    public void download(HttpServletResponse response, HttpServletRequest request, String filePath) {
        String fileName = getFileNameByPath(filePath);
        // 实际情况的文件路径中不包含桶名，可从数据库中获取
        String bucketName = StringUtils.split(filePath, "/")[0];
        String thisFilePath = StringUtils.split(filePath, "/")[1];
        // 设置响应头信息，告知浏览器下载文件
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileNameEncoding(fileName, request));
        // 放行Content-Disposition标头，设置权限访问
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(thisFilePath)
                .build());
        int len = -1;
        byte[] buffer = new byte[1024];
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
        while ((len = getObjectResponse.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        byte[] bytes = outputStream.toByteArray();
        ServletOutputStream stream = response.getOutputStream();
        stream.write(bytes);
        stream.flush();
    }

    /**
     * 批量文件下载，打包成压缩包（通过文件路径）
     * @param response
     * @param request
     * @param zipName 可为空
     */
    @SneakyThrows
    public void download(HttpServletResponse response, HttpServletRequest request, List<String> filePaths, String zipName) {
        String thisZipName = zipName.equals("") ? "default" : zipName;
        // 清除首部空白行
        response.reset();
        // 设置响应头信息，告知浏览器下载文件
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileNameEncoding(thisZipName, request));
        // 放行Content-Disposition标头，设置权限访问
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        BufferedOutputStream bufferedOS = new BufferedOutputStream(response.getOutputStream());
        ZipOutputStream zipOS = new ZipOutputStream(bufferedOS);
        response.setHeader("Access-Control-Allow-Origin", "*");
        for (String path : filePaths) {
            String fileName = getFileNameByPath(path);
            // 实际情况的文件路径中不包含桶名，可从数据库中获取
            String bucketName = StringUtils.split(path, "/")[0];
            String filePath = StringUtils.split(path, "/")[1];
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePath)
                    .build());
            byte[] buffer = new byte[1024];
            int len = 0;
            zipOS.putNextEntry(new ZipEntry(fileName));
            while ((len = inputStream.read(buffer)) > 0) {
                zipOS.write(buffer, 0, len);
            }
        }
        bufferedOS.close();
        zipOS.close();
    }

    /**
     * 单文件删除（多级目录，使用文件路径）
     * @param filePath
     * @return boolean
     */
    public boolean removeFile(String filePath) {
        String bucketName = StringUtils.split(filePath, "/")[0];
        String thisFilePath = StringUtils.split(filePath, "/")[1];
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(thisFilePath).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 批量文件删除
     * @param filePaths
     * @return Map
     */
    public Map<String, String> removeFile(List<String> filePaths) {
        Map<String,String > result = new HashMap<>();
        for (String path : filePaths) {
            String bucketName = StringUtils.split(path, "/")[0];
            String fileName = StringUtils.split(path, "/")[1];
            try {
                // 这样Minio提供了removeObjects方法，配合，我这里因为桶可能不唯一，暂定
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
                result.put(getFileNameByPath(path), "删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put(getFileNameByPath(path), "网络异常，删除失败");
            }
        }
        return result;
    }

    /**
     * 获取文件的预览地址
     * @param fileName 文件名
     * @param bucketName 桶名
     * @return 地址
     */
    public String getPreviewFileUrl(String fileName, String bucketName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置不同浏览器编码
     * @param fileName
     * @param request
     * Returns 编码
     */
    @SneakyThrows
    public static String fileNameEncoding(String fileName, HttpServletRequest request) {
        // 获得请求头中的User-Agent
        String agent = request.getHeader("User-Agent");
        // 根据不同的客户端进行不同的编码
        if (agent.contains("MSIE")) {
            // IE浏览器
            fileName = URLEncoder.encode(fileName, "utf-8");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            fileName = "=?utf-8?B?" + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            fileName = URLEncoder.encode(fileName, "utf-8");
        }
        return fileName;
    }
}
