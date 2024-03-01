package com.summer.commonmodule.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * 访问api Url
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * 访问密钥
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * 密钥
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 捅名称
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 创建Minio客户端
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
