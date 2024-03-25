package com.summer.manageserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.entity.dto.ShopSalesDTO;
import com.summer.commonmodule.entity.model.Image;
import com.summer.commonmodule.entity.model.ProductInfo;
import com.summer.commonmodule.entity.model.ProductRecord;
import com.summer.commonmodule.entity.model.ProductSpecs;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.ProductInfoMapper;
import com.summer.commonmodule.mapper.ProductRecordMapper;
import com.summer.commonmodule.mapper.ProductSpecsMapper;
import com.summer.commonmodule.mapper.ShoppingInfoMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.utils.MinioUtil;
import com.summer.manageserver.entity.bo.ProductRecordBO;
import com.summer.manageserver.entity.vo.ProductHeatOrSalesVO;
import com.summer.manageserver.entity.vo.ProductInfoVO;
import com.summer.manageserver.entity.vo.ProductRecordVO;
import com.summer.manageserver.entity.vo.ShopSalesVO;
import com.summer.manageserver.service.ProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 产品信息
 * @author WangLang
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ProductRecordMapper productRecordMapper;

    @Autowired
    private ProductSpecsMapper productSpecsMapper;

    @Autowired
    private ShoppingInfoMapper shoppingInfoMapper;

    @Autowired
    private MinioUtil minioUtil;

    /**
     * minio地址
     */
    @Value("${minio.endpoint}")
    private String minioUrl;
    /**
     * 桶名
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    @Override
    public ProductHeatOrSalesVO getProductHeatOrSales(String type) {
        if (CharSequenceUtil.isBlank(type)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        ProductHeatOrSalesVO productHeatOrSalesVO = new ProductHeatOrSalesVO();
        List<String> name = new ArrayList<>();
        List<Integer> value = new ArrayList<>();

        List<ProductRecordDTO> productRecordDTOS = productInfoMapper.queryProductInfo("", null, null, "productInfo." + type, Sort.Direction.DESC, 0, 6);
        for (ProductRecordDTO dto : productRecordDTOS) {
            name.add(dto.getName());
            value.add("heat".equals(type) ? dto.getProductInfo().getHeat() : dto.getProductInfo().getSold());
        }

        productHeatOrSalesVO.setName(name)
                .setValue(value);

        return productHeatOrSalesVO;
    }

    @Override
    public void addProductInfo(ProductInfoVO productInfoVO) {
        if (Objects.isNull(productInfoVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        ProductInfo productInfo = new ProductInfo();
        ProductRecord productRecord = new ProductRecord();
        ProductSpecs productSpecs = new ProductSpecs();

        // 上传文件到MinIO服务器并获取预览地址
        List<Image> images = uploadImage(productInfoVO.getImages());
        List<Image> carouseImages = uploadImage(productInfoVO.getCarouselImages());
        List<Image> physicalImages = uploadImage(productInfoVO.getPhysicalImages());
        List<Image> detailsImages = uploadImage(productInfoVO.getDetailsImages());

        String id = IdUtil.simpleUUID();

        productInfo.setProductId(id)
                .setName(productInfoVO.getName())
                .setIntroduce(productInfoVO.getIntroduce())
                .setIdentifier(productInfoVO.getIdentifier())
                .setFactoryNumber(productInfoVO.getIdentifier().split("-")[0])
                .setProducer(productInfoVO.getProducer())
                .setMaterialQuality(productInfoVO.getMaterialQuality())
                .setFiller(productInfoVO.getFiller())
                .setSpace(productInfoVO.getSpace())
                .setStyle(productInfoVO.getStyle())
                .setCategory(productInfoVO.getCategory())
                .setCarouselImages(carouseImages)
                .setPhysicalImages(physicalImages)
                .setDetailsImages(detailsImages);

        productRecord.setProductId(id)
                .setPrice(productInfoVO.getPrice())
                .setInventory(productInfoVO.getInventory());

        productSpecs.setProductId(id)
                .setSize(splitStr(productInfoVO.getSize()))
                .setColour(splitStr(productInfoVO.getColour()))
                .setMaterialType(splitStr(productInfoVO.getMaterialType()))
                .setImages(images);

        // 插入数据库
        ProductInfo info = productInfoMapper.insertProductInfo(productInfo);
        ProductRecord record = productRecordMapper.insertProductRecord(productRecord);
        ProductSpecs specs = productSpecsMapper.insertProductSpecs(productSpecs);
        if (Objects.isNull(info) || Objects.isNull(record) || Objects.isNull(specs)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "插入数据库错误", logger);
        }

    }

    @Override
    public ProductRecordVO getProductRecord(String field, String value, Integer page) {
        ProductRecordVO productRecordVO = new ProductRecordVO();
        List<ProductRecordBO> productRecordBOS = new ArrayList<>();

        // 计数
        long total = productInfoMapper.countProduct();

        // 当filed为空即查询全部
        List<ProductRecordDTO> productRecordDTOS = productInfoMapper.queryProductInfo(field, value, 10 * (page - 1), 10);
        for (ProductRecordDTO dto : productRecordDTOS) {
            ProductRecordBO productRecordBO = new ProductRecordBO();
            BeanUtil.copyProperties(dto, productRecordBO);
            productRecordBO.setPrice(dto.getProductInfo().getPrice())
                    .setInventory(dto.getProductInfo().getInventory())
                    .setHeat(dto.getProductInfo().getHeat())
                    .setScore(dto.getProductInfo().getScore())
                    .setCreationTime(dto.getProductInfo().getCreateTime())
                    .setImage(dto.getCarouselImages().get(0).getImage());
            productRecordBOS.add(productRecordBO);
        }

        productRecordVO.setProductRecordBOS(productRecordBOS)
                .setPage(page)
                .setTotal((int) total);

        return productRecordVO;
    }

    @Override
    public void delProductInfo(List<String> productId) {
        if (productId.isEmpty()) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        List<String> images = new ArrayList<>();
        for (String id : productId) {
            ProductInfo productInfo = productInfoMapper.queryProductInfoById(id);
            if (Objects.isNull(productInfo)) {
                continue;
            }
            // 添加图片路径
            addImagesPath(images, productInfo.getCarouselImages());
            addImagesPath(images, productInfo.getPhysicalImages());
            addImagesPath(images, productInfo.getDetailsImages());
        }

        // 批量删除图片
        minioUtil.removeFile(images);
        // 删除产品信息和购物车信息
        String[] split = productId.toString().substring(1, productId.toString().length() - 1).split(StrPool.COMMA + CharSequenceUtil.SPACE);
        long delCount = productInfoMapper.deleteProductInfoById(split);
        if ((int) delCount == 0) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "删除产品信息失败", logger);
        }
        productRecordMapper.deleteProductRecordById(split);
        productSpecsMapper.deleteProductSpecsById(split);
        shoppingInfoMapper.deleteShoppingByProductIds(split);
    }

    @Override
    public ProductInfoVO getProductById(String productId) {
        if (CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        ProductInfo productInfo = productInfoMapper.queryProductInfoById(productId);
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(productId);
        ProductSpecs productSpecs = productSpecsMapper.queryProductSpecsById(productId);
        if (Objects.isNull(productInfo)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "未查询到改产品", logger);
        }

        ProductInfoVO productInfoVO = new ProductInfoVO();
        productInfoVO.setName(productInfo.getName())
                .setIdentifier(productInfo.getIdentifier())
                .setIntroduce(productInfo.getIntroduce())
                .setProducer(productInfo.getProducer())
                .setMaterialQuality(productInfo.getMaterialQuality())
                .setFiller(productInfo.getFiller())
                .setStyle(productInfo.getStyle())
                .setCategory(productInfo.getCategory())
                .setSpace(productInfo.getSpace())
                .setPrice(productRecord.getPrice())
                .setInventory(productRecord.getInventory())
                .setSize(splitList(productSpecs.getSize()))
                .setColour(splitList(productSpecs.getColour()))
                .setMaterialType(splitList(productSpecs.getMaterialType()));

        return productInfoVO;
    }

    @Override
    public void updateProductInfo(ProductInfoVO productInfoVO) {
        if (Objects.isNull(productInfoVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 查询
        ProductInfo productInfo = productInfoMapper.queryProductInfoById(productInfoVO.getProductId());
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(productInfoVO.getProductId());
        ProductSpecs productSpecs = productSpecsMapper.queryProductSpecsById(productInfoVO.getProductId());

        if (Objects.isNull(productInfo) || Objects.isNull(productRecord) || Objects.isNull(productSpecs)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "未查询到改产品", logger);
        }

        // 上传文件到MinIO服务器并获取预览地址
        List<Image> images = productInfoVO.getImages().length == 0 ? null : uploadImage(productInfoVO.getImages());
        List<Image> carouseImages = productInfoVO.getCarouselImages().length == 0 ? null : uploadImage(productInfoVO.getCarouselImages());
        List<Image> physicalImages = productInfoVO.getPhysicalImages().length == 0 ? null : uploadImage(productInfoVO.getPhysicalImages());
        List<Image> detailsImages = productInfoVO.getDetailsImages().length == 0 ? null : uploadImage(productInfoVO.getDetailsImages());

        // 删除MinIO之前图片
        List<String> imagePath = new ArrayList<>();
        imagesPath(imagePath, productSpecs.getImages(), images);
        imagesPath(imagePath, productInfo.getCarouselImages(), carouseImages);
        imagesPath(imagePath, productInfo.getPhysicalImages(), physicalImages);
        imagesPath(imagePath, productInfo.getDetailsImages(), detailsImages);
        minioUtil.removeFile(imagePath);

        productInfo.setName(productInfoVO.getName())
                .setIntroduce(productInfoVO.getIntroduce())
                .setIdentifier(productInfoVO.getIdentifier())
                .setFactoryNumber(productInfoVO.getIdentifier().split("-")[0])
                .setProducer(productInfoVO.getProducer())
                .setMaterialQuality(productInfoVO.getMaterialQuality())
                .setFiller(productInfoVO.getFiller())
                .setSpace(productInfoVO.getSpace())
                .setStyle(productInfoVO.getStyle())
                .setCategory(productInfoVO.getCategory())
                .setCarouselImages(Objects.isNull(carouseImages) ? productInfo.getCarouselImages() : carouseImages)
                .setPhysicalImages(Objects.isNull(physicalImages) ? productInfo.getPhysicalImages() : physicalImages)
                .setDetailsImages(Objects.isNull(detailsImages) ? productInfo.getDetailsImages() : detailsImages);

        productRecord.setPrice(productInfoVO.getPrice())
                .setInventory(productInfoVO.getInventory());

        productSpecs.setSize(splitStr(productInfoVO.getSize()))
                .setColour(splitStr(productInfoVO.getColour()))
                .setMaterialType(splitStr(productInfoVO.getMaterialType()))
                .setImages(Objects.isNull(images) ? productSpecs.getImages() : images);

        // 保存
        ProductInfo info = productInfoMapper.saveProductInfo(productInfo);
        ProductRecord record = productRecordMapper.saveProductRecord(productRecord);
        ProductSpecs specs = productSpecsMapper.saveProductSpecs(productSpecs);
        if (Objects.isNull(info) || Objects.isNull(record) || Objects.isNull(specs)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "保存数据库错误", logger);
        }

    }

    /**
     * 上传图片信息
     * @param images 图片文件
     * @return 图片信息
     */
    private List<Image> uploadImage(MultipartFile[] images) {
        List<Image> imageList = new ArrayList<>();

        List<Map<String, String>> upload = minioUtil.upload(images, bucketName);
        for (Map<String, String> map : upload) {
            // 这是临时路径，暂不使用
//            String fileUrl = minioUtil.getPreviewFileUrl(map.get("fileName"), bucketName);
            Image image = new Image();
            // 采用绝对路径
            image.setImage(minioUrl + StrPool.SLASH + map.get("filePath"))
                    .setImagePath(map.get("filePath"));
            imageList.add(image);
        }

        return imageList;
    }

    /**
     * 组装图片路径
     * @param imagePath 图片路径
     * @param images 目标
     */
    private void imagesPath(List<String> imagePath, List<Image> images, List<Image> check) {
        if (Objects.nonNull(check)) {
            for (Image image : images) {
                imagePath.add(image.getImagePath());
            }
        }
    }

    /**
     * 分割字符串
     * @param str 字符串
     * @return 字符集合
     */
    private List<String> splitStr(String str) {
        List<String> strings = new ArrayList<>();
        String[] s = str.split(" ");
        for (String i : s) {
            strings.add(i);
        }

        return strings;
    }

    /**
     * 拼接字符串
     * @param list 数组
     * @return 字符串
     */
    private String splitList(List<String> list) {
        StringBuilder string = new StringBuilder();
        for (String s : list) {
            string.append(s).append(" ");
        }

        return string.substring(0, string.length() - 1);
    }

    /**
     * 添加图片路径
     * @param images 添加集合
     * @param list 目标集合
     */
    private void addImagesPath(List<String> images, List<Image> list) {
        for (Image image : list) {
            images.add(image.getImagePath());
        }
    }
}
