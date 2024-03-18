package com.summer.manageserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 产品销售信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShopSalesVO {

    /**
     * 月份
     */
    private String name;

    /**
     * 销售值
     */
    private Integer value;

}
