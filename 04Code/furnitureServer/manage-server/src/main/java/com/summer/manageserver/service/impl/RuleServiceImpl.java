package com.summer.manageserver.service.impl;

import com.summer.commonmodule.entity.bo.HomeDataInfoBO;
import com.summer.commonmodule.entity.bo.TokenExpireTimeBO;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.manageserver.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 规则信息
 * @author WangLang
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 最短过期时间
     */
    private static final Integer SHORT_EXPIRE_TIME = 600;

    /**
     * Token存储KEY
     */
    private static final String TOKEN_EXPIRE_KEY = "tokenExpireTime";

    /**
     * 微信小程序主页数据key，存储在Redis中
     */
    private static final String HOME_DATA_KEY = "homeData";

    private static final Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);


    @Override
    public void setTokenExpireTime(Integer accessTokenExpireTime, Integer refreshTokenExpireTime) {
        if (Objects.isNull(accessTokenExpireTime) || Objects.isNull(refreshTokenExpireTime)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }
        if (accessTokenExpireTime < SHORT_EXPIRE_TIME || accessTokenExpireTime > refreshTokenExpireTime) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "时间过小或accessToken过期时间大于refreshToken过期时间", logger);
        }

        // 查询过期时间，存在则删除
        boolean tokenExpireKey = redisUtil.hasKey(TOKEN_EXPIRE_KEY);
        if (tokenExpireKey) {
            redisUtil.del(TOKEN_EXPIRE_KEY);
        }

        TokenExpireTimeBO tokenExpireTimeBO = new TokenExpireTimeBO();
        tokenExpireTimeBO.setAccessTokenExpireTime(accessTokenExpireTime)
                .setRefreshTokenExpireTime(refreshTokenExpireTime);
        // 存储
        boolean setTokenExpireKey = redisUtil.set(TOKEN_EXPIRE_KEY, tokenExpireTimeBO);
        if (!setTokenExpireKey) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "存储Redis失败", logger);
        }

    }

    @Override
    public List<HomeDataInfoBO> getWeChatHomeDataCategory() {
        List<HomeDataInfoBO> homeDataInfoBOS = new ArrayList<>();
        // 从Redis中查询
        boolean weChatHoneDataCategoryKey = redisUtil.hasKey(HOME_DATA_KEY);
        if (weChatHoneDataCategoryKey) {
            List<Object> objects = redisUtil.lGet(HOME_DATA_KEY, 0, -1);
            for (Object object : objects) {
                homeDataInfoBOS.add((HomeDataInfoBO) object);
            }
        }

        return homeDataInfoBOS;
    }

    @Override
    public void saveWeChatHomeDataCategory(List<HomeDataInfoBO> homeDataInfoBOS) {
        // 如果为空则删除Redis中的信息
        if (homeDataInfoBOS.isEmpty()) {
            redisUtil.del(HOME_DATA_KEY);
            return;
        }

        // 判断是否存在，存在则删除，或者为空即删除
        boolean homeDataKey = redisUtil.hasKey(HOME_DATA_KEY);
        if (homeDataKey) {
            redisUtil.del(HOME_DATA_KEY);
        }

        for (HomeDataInfoBO homeDataInfoBO : homeDataInfoBOS) {
            boolean setHomeDataKey = redisUtil.lSet(HOME_DATA_KEY, homeDataInfoBO);if (!setHomeDataKey) {
                RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "存储Redis失败", logger);
            }
        }

    }
}
