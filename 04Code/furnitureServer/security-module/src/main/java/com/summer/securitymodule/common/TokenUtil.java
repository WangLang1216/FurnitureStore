package com.summer.securitymodule.common;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.CryptoException;
import com.summer.commonmodule.entity.bo.TokenExpireTimeBO;
import com.summer.commonmodule.entity.bo.TokenInfoBO;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.utils.EncryptionUtil;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.securitymodule.entity.bo.TokenBO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Token创建-校验
 * @author WangLang
 */
@Component
public class TokenUtil {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    /**
     * 默认请求Token和刷新Token过期时间
     */
    private static final Integer ACCESS_EXPIRES_IN = 30 * 60;
    private static final Integer REFRESH_EXPIRES_IN = 24 * 60 * 60;

    /**
     * Token存储KEY
     */
    private static final String TOKEN_EXPIRE_KEY = "tokenExpireTime";

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 创建Token
     * @param tokenType token类型
     * @return TokenBO
     */
    public TokenBO createToken(Boolean tokenType) {
        TokenBO tokenBO = new TokenBO();

        // 创建accessToken和refreshToken
        String token = IdUtil.simpleUUID();

        // 查询Redis中过期时间
        TokenExpireTimeBO tokenExpireTimeBO = new TokenExpireTimeBO();
        boolean tokenExpireKey = redisUtil.hasKey(TOKEN_EXPIRE_KEY);
        if (tokenExpireKey) {
            tokenExpireTimeBO = (TokenExpireTimeBO) redisUtil.get(TOKEN_EXPIRE_KEY);
        }

        // 根据tokenType判断是accessToken还是refreshToken，时间不同，true为accessToken时间，false为refreshToken时间
        int expiresTime = Boolean.TRUE.equals(tokenType) ? ACCESS_EXPIRES_IN : REFRESH_EXPIRES_IN;
        if (tokenExpireKey) {
            expiresTime = Boolean.TRUE.equals(tokenType) ? tokenExpireTimeBO.getAccessTokenExpireTime() : tokenExpireTimeBO.getRefreshTokenExpireTime();
        }

        tokenBO.setToken(token)
                .setExpiresIn(expiresTime);

        return tokenBO;
    }

    /**
     * 校验Token
     * @param token 令牌
     * @return Boolean
     */
    public Boolean verifyToken(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 解密Token
        String decodeToken;
        try {
            decodeToken = encryptionUtil.publicKeyDecryption(token);
        } catch (CryptoException e) {
            // 解码失败则表示服务重启，清空Redis
            redisUtil.clearAllKeys();
            return Boolean.FALSE;
        }

        // 查询Redis中是否存在该key
        return redisUtil.hasKey(decodeToken);
    }

    /**
     * 获取Token中的信息
     * @param token 令牌
     * @return TokenInfoBO
     */
    public TokenInfoBO getTokenInfo(String token) {
        boolean verifyToken = verifyToken(token);

        if (!verifyToken) {
            RecordLoggerThrowException.record(ResponseEnum.UNAUTHORIZED, logger);
        }

        // 解密Token
        String decodeToken = encryptionUtil.publicKeyDecryption(token);

        return (TokenInfoBO) redisUtil.get(decodeToken);
    }


}
