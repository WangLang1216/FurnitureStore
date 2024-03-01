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
 * 顾客信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("customer")
public class Customer extends BaseModel implements Serializable {

    @Id
    private String userId;

    /**
     * 微信用户唯一ID
     */
    @Field("open_id")
    private String openId;

    /**
     * 昵称
     */
    @Field("nickname")
    private String nickname;

    /**
     * 手机号
     */
    @Field("phone")
    private String phone;

    /**
     * 头像
     */
    @Field("picture")
    private String picture;

    /**
     * 状态，true为正常，默认为true
     */
    @Field("state")
    private Boolean state = true;

}
