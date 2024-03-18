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
 * 产品数据记录表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("product_record")
public class ProductRecord extends BaseModel implements Serializable {

    @Id
    private String productId;

    /**
     * 单价
     */
    @Field("price")
    private Integer price;

    /**
     * 已售数量
     */
    @Field("sold")
    private Integer sold = 0;

    /**
     * 热度
     */
    @Field("heat")
    private Integer heat = 0;

    /**
     * 收藏用户
     */
    @Field("collect")
    private String collect;

    /**
     * 库存
     */
    @Field("inventory")
    private Integer inventory;

    /**
     * 综合得分值
     */
    @Field("score")
    private Double score = 0d;

}
