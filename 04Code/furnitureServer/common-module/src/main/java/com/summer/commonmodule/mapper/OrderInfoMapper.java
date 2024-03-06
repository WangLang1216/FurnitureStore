package com.summer.commonmodule.mapper;

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


}
