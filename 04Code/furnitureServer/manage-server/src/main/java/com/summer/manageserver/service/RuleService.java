package com.summer.manageserver.service;

import com.summer.commonmodule.entity.bo.HomeDataInfoBO;

import java.util.List;

/**
 * 规则信息
 * @author WangLang
 */
public interface RuleService {

    /**
     * 设置Token过期时间
     * @param accessTokenExpireTime 请求Token时间
     * @param refreshTokenExpireTime 刷新Token时间
     */
    void setTokenExpireTime(Integer accessTokenExpireTime, Integer refreshTokenExpireTime);

    /**
     * 获取微信主页数据品类
     * @return 微信小程序主页数据品类信息集合
     */
    List<HomeDataInfoBO> getWeChatHomeDataCategory();

    /**
     * 保存微信主页数据品类信息
     * @param homeDataInfoBOS 微信小程序主页数据品类信息集合
     */
    void saveWeChatHomeDataCategory(List<HomeDataInfoBO> homeDataInfoBOS);

}
