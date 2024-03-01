package com.summer.securitymodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 存储Token信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StoreTokenInfoBO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 系统类型
     */
    private Integer sysType;

    /**
     * 用户状态
     */
    private Boolean state;

    /**
     * 是否为首次登录，true表示首次登录，false否之
     */
    private Boolean firstLogin;

}
