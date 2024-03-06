package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.OrderInfo;
import com.summer.commonmodule.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 订单信息
 */
@Repository
public class OrderInfoMapperImpl implements OrderInfoMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public OrderInfo insertOrderInfo(OrderInfo orderInfo) {
        Date date = new Date();
        orderInfo.setCreateTime(date)
                .setModifiedTime(date);
        return mongoTemplate.insert(orderInfo);
    }
}
