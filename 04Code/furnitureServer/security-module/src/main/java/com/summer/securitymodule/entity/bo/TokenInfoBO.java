package com.summer.securitymodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Token信息类
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenInfoBO {

    /**
     * 保存在Token中的用户信息
     */
    private UserInfoTokenBO userInfoTokenBO;

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

}
