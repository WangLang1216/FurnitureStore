package com.summer.manageserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 产品热度/销售信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductHeatOrSalesVO {

    /**
     * 产品名称
     */
    private List<String> name;

    /**
     * 产品热度或销量值
     */
    private List<Integer> value;

}
