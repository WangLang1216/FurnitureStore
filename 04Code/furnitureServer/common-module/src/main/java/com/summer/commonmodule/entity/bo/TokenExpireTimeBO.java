package com.summer.commonmodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Token过期时间，存储在Redis中
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenExpireTimeBO {

    /**
     * 请求Token过期时间
     */
    private Integer accessTokenExpireTime;

    /**
     * 刷新Token过期时间
     */
    private Integer refreshTokenExpireTime;

}
