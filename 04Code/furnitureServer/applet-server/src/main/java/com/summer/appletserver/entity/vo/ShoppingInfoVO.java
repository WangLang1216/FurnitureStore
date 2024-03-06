package com.summer.appletserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 购物车信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShoppingInfoVO {

    /**
     * 产品选择，默认false
     */
    private Boolean isSelect;

    /**
     * 购物车ID
     */
    private String shoppingId;

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
     * 产品数量
     */
    private Integer quantity;

    /**
     * 产品单价
     */
    private Integer price;
}
