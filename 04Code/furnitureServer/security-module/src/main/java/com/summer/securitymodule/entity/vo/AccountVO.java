package com.summer.securitymodule.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 账号信息表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AccountVO {

    /**
     * 账号
     */
    @NotBlank
    private String account;

    /**
     * 密码
     */
    @NotBlank
    private String password;

}
