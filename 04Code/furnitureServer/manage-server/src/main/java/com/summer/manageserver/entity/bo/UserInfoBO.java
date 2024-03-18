package com.summer.manageserver.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoBO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户角色
     */
    private Integer role;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账号状态
     */
    private Boolean state;

    /**
     * 创建日期
     */
    private Date createTime;

}
