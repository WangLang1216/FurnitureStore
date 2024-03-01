package com.summer.securitymodule.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * 密码加密
 * @author WangLang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "encoder.crypt")
public class PasswordConfig {

    /**
     * 加密强度
     */
    @Value("${encoder.crypt.strength}")
    private int strength;

    /**
     * 干扰因子
     */
    @Value("${encoder.crypt.secret}")
    private String secret;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 对干扰因子加密
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        // 对密码加密
        return new BCryptPasswordEncoder(strength, secureRandom);
    }

}
