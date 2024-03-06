package com.summer.securitymodule.common;

import cn.hutool.core.text.CharSequenceUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.summer.commonmodule.entity.bo.UserInfoTokenBO;
import com.summer.commonmodule.exception.BusinessException;
import com.summer.commonmodule.response.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author WangLang
 */
@Component
public class JwtTokenUtil {

    /**
     * 私钥
     */
    private String SECRET = "SECRET";

    /**
     * 过期时间，时间与刷新REFRESH_TIME相同
     */
    private Integer EXPIRE_TIME = 60;

    /**
     * 密钥
     */
    private Algorithm algorithm = Algorithm.HMAC256(SECRET);

    /**
     * 头部信息
     */
    private Map<String, Object> header = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * 根据Token类型进行Token创建，并存储用户ID
     * @param userId 用户ID
     * @param tokenType Token类型 true为刷新Token，false为校验Token
     * @return Token
     */
    public String createToken(UserInfoTokenBO userInfoTokenBO) {
        String token;
        header.put("typ", "JWT");header.put("alg", "HMAC256");
        Instant instant = LocalDateTime.now().plusSeconds(EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant();
        Date expire = Date.from(instant);
        token = JWT.create().withHeader(header)
                .withClaim("userId", userInfoTokenBO.getUserId())
                .withClaim("sysType", userInfoTokenBO.getSysType())
                .withIssuer("SERVICE")
                .withNotBefore(new Date())
                .withExpiresAt(expire)
                .withIssuedAt(new Date(System.nanoTime()))
                .sign(algorithm);

        return token;
    }

    /**
     * Token校验
     * @param token 令牌
     * @return Boolean
     */
    public Boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            logger.error("该令牌校验错误：{}，错误信息为：{}", token, e);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 获取Token中的用户ID
     * @param token 令牌
     * @return 用户Token信息
     */
    public UserInfoTokenBO getUserInfoByToken(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            logger.error("令牌为空");
            throw new BusinessException(ResponseEnum.UNAUTHORIZED);
        }
        boolean verifyToken = verifyToken(token);
        if (verifyToken) {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            UserInfoTokenBO userInfoTokenBO = new UserInfoTokenBO();
            userInfoTokenBO.setUserId(jwt.getClaim("userId").asString());
            userInfoTokenBO.setSysType(jwt.getClaim("sysType").asInt());

            return userInfoTokenBO;
        }

        throw new BusinessException(ResponseEnum.TOKEN_HAS_EXPIRED);
    }

}
