package com.summer.commonmodule.service;

/**
 * 手机验证码信息
 * @author WangLang
 */
public interface PhoneCodeService {

    /**
     * 校验手机验证码
     * @param phone 手机号
     * @param code 验证码
     */
    void checkCode(String phone, String code);


}
