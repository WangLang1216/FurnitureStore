package com.summer.securitymodule.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 手机号验证码信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PhoneCodeVO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 系统类型，true表示微信小程序，false为管理后台
     */
    private Boolean sysType;

    /**
     * 微信用户通信凭证码，仅sysType为true才携带
     */
    private String weChatCode;
}
