package com.summer.commonmodule.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单状态信息DTO
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderStateDTO {

    /**
     * 状态
     */
    private Integer state;

    /**
     * 总数
     */
    private Integer count;

}
