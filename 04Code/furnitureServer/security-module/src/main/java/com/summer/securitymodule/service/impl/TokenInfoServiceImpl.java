package com.summer.securitymodule.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.securitymodule.common.EncryptionUtil;
import com.summer.securitymodule.common.TokenUtil;
import com.summer.securitymodule.entity.bo.TokenBO;
import com.summer.securitymodule.entity.bo.TokenInfoBO;
import com.summer.securitymodule.entity.bo.UserInfoTokenBO;
import com.summer.securitymodule.entity.bo.UserTokenBO;
import com.summer.securitymodule.entity.vo.TokenInfoVO;
import com.summer.securitymodule.service.TokenInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Token信息管理
 * @author WangLang
 */
@Service
public class TokenInfoServiceImpl implements TokenInfoService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    private static final Logger logger = LoggerFactory.getLogger(TokenInfoServiceImpl.class);

    @Override
    public TokenInfoBO createAndStoreToken(UserInfoTokenBO userInfoTokenBO) {
        if (Objects.isNull(userInfoTokenBO)) {
            RecordLoggerThrowException.record(ResponseEnum.USERINFO_IS_EMPTY, logger);
        }

        // 查询是否已存在该用户的Token信息，存在则删除
        boolean tokenKey = redisUtil.hasKey(userInfoTokenBO.getUserId());
        if (tokenKey) {
            UserTokenBO userTokenBO = (UserTokenBO) redisUtil.get(userInfoTokenBO.getUserId());
            redisUtil.del(userTokenBO.getAccessToken(), userTokenBO.getRefreshToken());
        }

        // 创建AccessToken
        TokenBO accessToken = tokenUtil.createToken(true);
        // 创建RefreshToken
        TokenBO refreshToken = tokenUtil.createToken(false);

        TokenInfoBO tokenInfoBO = new TokenInfoBO();
        tokenInfoBO.setUserInfoTokenBO(userInfoTokenBO)
                .setAccessToken(accessToken.getToken())
                .setRefreshToken(refreshToken.getToken())
                .setExpiresIn(accessToken.getExpiresIn());

        // 存储在Redis中
        boolean accessTokenSet = redisUtil.set(accessToken.getToken(), tokenInfoBO, accessToken.getExpiresIn());
        boolean refreshTokenSet = redisUtil.set(refreshToken.getToken(), tokenInfoBO, refreshToken.getExpiresIn());
        if (!accessTokenSet || !refreshTokenSet) {
            redisUtil.del(accessToken.getToken(), refreshToken.getToken());
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        // 存储该用户的Token信息防止重复登录多次生成Token
        UserTokenBO userTokenBO = new UserTokenBO();
        userTokenBO.setAccessToken(accessToken.getToken())
                .setRefreshToken(refreshToken.getToken());
        boolean userInfoTokenSet = redisUtil.set(userInfoTokenBO.getUserId(), userTokenBO);
        if (!userInfoTokenSet) {
            redisUtil.del(accessToken.getToken(), refreshToken.getToken());
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        return tokenInfoBO;
    }

    @Override
    public Boolean verifyToken(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            RecordLoggerThrowException.record(ResponseEnum.UNAUTHORIZED, logger);
        }

        // 校验Token
        return tokenUtil.verifyToken(token);
    }

    @Override
    public TokenInfoVO refreshToken(String refreshToken) {
        if (CharSequenceUtil.isBlank(refreshToken)) {
            RecordLoggerThrowException.record(ResponseEnum.UNAUTHORIZED, logger);
        }

        // 校验Token
        boolean verifyToken = tokenUtil.verifyToken(refreshToken);
        if (!verifyToken) {
            RecordLoggerThrowException.record(ResponseEnum.UNAUTHORIZED, logger);
        }

        // 解密Token
        String token = encryptionUtil.publicKeyDecryption(refreshToken);

        TokenInfoBO tokenInfo = (TokenInfoBO) redisUtil.get(token);
        UserTokenBO userTokenBO = (UserTokenBO) redisUtil.get(tokenInfo.getUserInfoTokenBO().getUserId());

        // 查询是否已经存在accessToken，存在则删除，防止重复生成
        boolean accessTokenKey = redisUtil.hasKey(userTokenBO.getAccessToken());
        if (accessTokenKey) {
            redisUtil.del(userTokenBO.getAccessToken());
        }

        // 创建AccessToken并存储
        TokenBO accessToken = tokenUtil.createToken(true);
        TokenInfoBO tokenInfoBO = new TokenInfoBO();
        tokenInfoBO.setUserInfoTokenBO(tokenInfo.getUserInfoTokenBO())
                .setAccessToken(accessToken.getToken())
                .setRefreshToken(token)
                .setExpiresIn(accessToken.getExpiresIn());

        userTokenBO.setAccessToken(accessToken.getToken());
        redisUtil.del(tokenInfo.getUserInfoTokenBO().getUserId());
        boolean userTokenSet = redisUtil.set(tokenInfo.getUserInfoTokenBO().getUserId(), userTokenBO);
        boolean accessTokenSet = redisUtil.set(accessToken.getToken(), tokenInfoBO, accessToken.getExpiresIn());
        if (!userTokenSet || !accessTokenSet) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        // 返回前端TokenInfoVO
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        BeanUtils.copyProperties(tokenInfoBO, tokenInfoVO);
        // 加密Token
        tokenInfoVO.setAccessToken(encryptionUtil.privateKeyEncryption(tokenInfoVO.getAccessToken()))
                .setRefreshToken(encryptionUtil.privateKeyEncryption(tokenInfoVO.getRefreshToken()));

        return tokenInfoVO;
    }
}
