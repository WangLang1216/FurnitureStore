package com.summer.securitymodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 保存在Token信息里面的用户信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoTokenBO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 系统类型
     * @see com.summer.securitymodule.constant.SysTypeEnum
     */
    private Integer sysType;

}
