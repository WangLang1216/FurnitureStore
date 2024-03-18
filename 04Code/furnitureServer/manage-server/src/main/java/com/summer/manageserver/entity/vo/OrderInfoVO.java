package com.summer.manageserver.entity.vo;

import com.summer.manageserver.entity.bo.OrderInfoBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 产订单信息集合
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderInfoVO {

    private List<OrderInfoBO> orderInfoBOS;

    private Integer page;

    private Integer total;

}
