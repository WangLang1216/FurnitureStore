package com.summer.appletserver.service;

import com.summer.appletserver.entity.vo.UserInfoVO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;

/**
 * 用户信息
 * @author WangLang
 */
public interface UserService {

    /**
     *  保存用户信息
     * @param token 令牌
     * @param userInfoVO 用户信息
     */
    void saveUserInfo(String token, UserInfoVO userInfoVO);

    /**
     * 更改用户手机号信息
     * @param token 令牌
     * @param phoneCodeVO 手机号验证码信息
     */
    void saveUserPhone(String token, PhoneCodeVO phoneCodeVO);

    /**
     * 用户绑定微信
     * @param token 令牌
     * @param code 微信凭证
     */
    void bindUserWeChat(String token, String code);

}
