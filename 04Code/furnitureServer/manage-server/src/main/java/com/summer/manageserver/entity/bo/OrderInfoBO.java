package com.summer.manageserver.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class OrderInfoBO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品尺寸
     */
    private String productSize;

    /**
     * 产品图片
     */
    private String image;

    /**
     * 产品颜色
     */
    private String productColour;

    /**
     * 产品材质
     */
    private String materialType;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 订单状态，0为待付款，1为待确定，2为待收货，3为待完成，-1为退货/售后
     */
    private Integer state;

    /**
     * 日期
     */
    private Date createTime;

}
