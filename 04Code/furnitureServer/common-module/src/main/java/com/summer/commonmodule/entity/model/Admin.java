package com.summer.commonmodule.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 管理平台用户信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("admins")
public class Admin extends BaseModel implements Serializable {

    @Id
    @Field("user_id")
    private String userId;

    /**
     * 账号
     */
    @Field("account")
    private String account;

    /**
     * 加密后的密码
     */
    @Field("password")
    private String password;

    /**
     * 手机号
     */
    @Field("phone")
    private String phone;

    /**
     * 账号角色，0表示普通用户，1表示管理员，管理员可进行Web管理平台的账号管理
     */
    @Field("role")
    private Integer role;

    /**
     * 账号状态，true为正常，默认为true
     */
    @Field("state")
    private Boolean state = true;

}
