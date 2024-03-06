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
 * 购物车信息表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("shopping_info")
public class ShoppingInfo extends BaseModel implements Serializable {

    @Id
    private String shoppingId;

    /**
     * 用户id
     */
    @Field("user_id")
    private String userId;

    /**
     * 产品id
     */
    @Field("product_id")
    private String productId;

    /**
     * 产品名称
     */
    @Field("product_name")
    private String productName;

    /**
     * 产品尺寸
     */
    @Field("product_size")
    private String productSize;

    /**
     * 产品图片
     */
    @Field("image")
    private String image;

    /**
     * 产品颜色
     */
    @Field("product_colour")
    private String productColour;

    /**
     * 产品材质
     */
    @Field("material_type")
    private String materialType;

    /**
     * 数量
     */
    @Field("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @Field("price")
    private Integer price;

}
