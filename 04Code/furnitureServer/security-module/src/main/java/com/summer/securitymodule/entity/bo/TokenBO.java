package com.summer.securitymodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Token信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenBO {

    /**
     * 校验Token
     */
    private String token;

    /**
     * 过期时间
     */
    private Integer expiresIn;
}
