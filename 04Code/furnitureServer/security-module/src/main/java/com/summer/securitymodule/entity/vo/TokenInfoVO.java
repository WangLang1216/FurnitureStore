package com.summer.securitymodule.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Token信息，该信息返回给前端，前端请求携带accessToken进行用户校验
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenInfoVO {

    /**
     * 校验Token
     */
    private String accessToken;

    /**
     * 刷新Token
     */
    private String refreshToken;

    /**
     * 过期时间
     */
    private Integer expiresIn;

    /**
     * 是否为首次登录，true表示首次登录，false否之
     */
    private Boolean firstLogin;

}
