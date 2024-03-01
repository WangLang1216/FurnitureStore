package com.summer.securitymodule.service;

import com.summer.securitymodule.entity.vo.AccountVO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import com.summer.securitymodule.entity.vo.TokenInfoVO;

/**
 * 账号登录-登出-短信
 * @author WangLang
 */
public interface AccountService {

    /**
     * 微信小程序用户微信登录
     * @param code 微信用户唯一码
     * @return Token信息
     */
    TokenInfoVO login(String code);

    /**
     * 管理平台登录
     * @param accountVO 账号信息
     * @return Token信息
     */
    TokenInfoVO login(AccountVO accountVO);

    /**
     * 手机号验证码登录
     * @param phoneCodeVO 手机号验证码信息
     * @return Token信息
     */
    TokenInfoVO login(PhoneCodeVO phoneCodeVO);

    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    void sendSmsCode(String phone);

    /**
     * 登出
     * @param token 令牌
     */
    void logout(String token);

}
