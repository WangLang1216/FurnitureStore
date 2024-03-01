package com.summer.appletserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoVO {

    /**
     * 昵称
     */
    @Field("nickname")
    private String nickname;

    /**
     * 头像
     */
    @Field("picture")
    private MultipartFile picture;

}
