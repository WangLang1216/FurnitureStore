package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.dto.OrderStateDTO;
import com.summer.commonmodule.entity.dto.OrderUserDTO;
import com.summer.commonmodule.entity.dto.ShopSalesDTO;
import com.summer.commonmodule.entity.model.OrderInfo;

import java.util.List;

/**
 * 订单信息
 * @author WangLang
 */
public interface OrderInfoMapper {

    /**
     * 新增订单信息
     * @param orderInfo 订单信息
     * @return 订单信息
     */
    OrderInfo insertOrderInfo(OrderInfo orderInfo);

    /**
     * 状态分组查询
     * @return 订单状态信息集合
     */
    List<OrderStateDTO> getOrderState();

    /**
     * 查询店铺销售信息
     * @return 店铺统计信息集合
     */
    List<ShopSalesDTO> queryShopSales();

    /**
     * 查询总数
     * @return 总数
     */
    Long countOrder();

    /**
     * 查询order_info表和customer表数据
     * @param field 字段名称
     * @param fieldValue 字段值
     * @param skip 跳过
     * @param limit 页数
     * @return order_info表和customer表数据集合
     */
    List<OrderUserDTO> queryOrderUser(String field, String fieldValue, Integer skip, Integer limit);

    /**
     * 批量修改订单状态
     * @param orderIds 订单ID集合
     * @param state 状态值
     * @return 修改数量
     */
    Long updateOrderState(String[] orderIds, Integer state);

}
