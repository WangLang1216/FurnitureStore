package com.summer.appletserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 修改购物车数量
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShoppingUpdateVO {

    /**
     * 购物车ID
     */
    private String shoppingId;

    /**
     * 数量
     */
    private Integer quantity;

}
