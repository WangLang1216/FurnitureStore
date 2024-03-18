package com.summer.commonmodule.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.summer.commonmodule.entity.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 产订单信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderUserDTO {


    @Id
    private String orderId;

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

    /**
     * 订单状态，0为待付款，1为待确定，2为待收货，3为待完成，-1为退货/售后
     */
    @Field("state")
    private Integer state;

    /**
     * 创建时间
     */
    @Field("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 更新时间
     */
    @Field("modified_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date modifiedTime;

    /**
     * 用户信息
     */
    private Customer customer;

}
