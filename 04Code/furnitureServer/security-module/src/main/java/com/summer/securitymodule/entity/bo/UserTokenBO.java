package com.summer.securitymodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Token信息，用于检索同一用户多次登录，生成多个Token信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserTokenBO {

    /**
     * 校验Token
     */
    private String accessToken;

    /**
     * 刷新Token
     */
    private String refreshToken;

}
