package com.summer.manageserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单状态数据
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderStateVO {

    /**
     * 已下单
     */
    private Integer ordered;

    /**
     * 已确认
     */
    private Integer determined;

    /**
     * 待完成
     */
    private Integer completed;

    /**
     * 待售后
     */
    private Integer afterSales;

    /**
     * 总数
     */
    private Integer total;

}
