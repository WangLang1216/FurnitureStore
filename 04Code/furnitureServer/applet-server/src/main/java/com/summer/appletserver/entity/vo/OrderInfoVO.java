package com.summer.appletserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderInfoVO {

    /**
     * 购物车ID集合
     */
    private String shoppingIds;

    /**
     * 订单状态，true为已支付，false为未支付
     */
    private Boolean orderState;

}
