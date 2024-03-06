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
import java.util.List;


/**
 * 用户喜好表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("user_like")
public class UserLike extends BaseModel implements Serializable {

    @Id
    private String ulId;

    /**
     * 喜好列表
     */
    @Field("like_products")
    private List<LikeProduct> likeProducts;

    /**
     * 总数
     */
    @Field("total")
    private Integer total;

}
