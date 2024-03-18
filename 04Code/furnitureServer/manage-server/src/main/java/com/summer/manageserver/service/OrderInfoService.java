package com.summer.manageserver.service;

import com.summer.manageserver.entity.vo.*;

import java.util.List;

/**
 * 订单信息
 * @author WangLang
 */
public interface OrderInfoService {

    /**
     * 查询订单状态
     * @return 订单状态数据
     */
    OrderStateVO getOrderState();

    /**
     * 查询店铺销售信息
     * @return 产品销售信息集合
     */
    List<ShopSalesVO> getShopSales();

    /**
     * 查询订单信息
     * @param field 字段
     * @param value 字段值
     * @param page 页码
     * @return 订单信息集合
     */
    OrderInfoVO getOrderInfo(String field, String value, Integer page);

    /**
     * 修改订单状态
     * @param orderStateUpdateVO 订单修改状态信息
     */
    void updateOrderState(OrderStateUpdateVO orderStateUpdateVO);


}
