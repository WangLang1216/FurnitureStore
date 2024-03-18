package com.summer.commonmodule.mapper.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.entity.dto.OrderStateDTO;
import com.summer.commonmodule.entity.dto.OrderUserDTO;
import com.summer.commonmodule.entity.dto.ShopSalesDTO;
import com.summer.commonmodule.entity.model.BaseModel;
import com.summer.commonmodule.entity.model.OrderInfo;
import com.summer.commonmodule.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<OrderStateDTO> getOrderState() {
        Aggregation aggregation = Aggregation.newAggregation(
                //按分类名称 统计 销售数量
                Aggregation.group("state").first("state").as("state").count().as("count"),
                Aggregation.project("state", "count"));

        return mongoTemplate.aggregate(aggregation, OrderInfo.class, OrderStateDTO.class).getMappedResults();
    }

    @Override
    public List<ShopSalesDTO> queryShopSales() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("state").is(4)),
                Aggregation.project("quantity", "price", "create_time").and("quantity").multiply("$price").as("amass")
                        .andExpression("substr(create_time, 5, 2)").as("date"),
                Aggregation.group("date").sum("amass").as("total"),
                Aggregation.sort(Sort.Direction.ASC, "date"));

        return mongoTemplate.aggregate(aggregation, "order_info", ShopSalesDTO.class).getMappedResults();
    }

    @Override
    public Long countOrder() {
        return mongoTemplate.count(new Query(), OrderInfo.class);
    }

    @Override
    public List<OrderUserDTO> queryOrderUser(String field, String fieldValue, Integer skip, Integer limit) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("customer")
                .localField("user_id")
                .foreignField("_id")
                .as("customer");

        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "create_time");
        SkipOperation skipOperation = Aggregation.skip(skip);
        LimitOperation limitOperation = Aggregation.limit(limit );
        if (CharSequenceUtil.isBlank(field) || Objects.isNull(field)) {
            Aggregation aggregation = Aggregation.newAggregation(lookupOperation, sort, skipOperation, limitOperation);

            return mongoTemplate.aggregate(aggregation, "order_info", OrderUserDTO.class).getMappedResults();
        }
        MatchOperation match = Aggregation.match(Criteria.where(field).regex(fieldValue));
        Aggregation aggregation = Aggregation.newAggregation(match, lookupOperation, sort, skipOperation, limitOperation);

        return mongoTemplate.aggregate(aggregation, "order_info", OrderUserDTO.class).getMappedResults();
    }

    @Override
    public Long updateOrderState(String[] orderIds, Integer state) {
        BaseModel baseModel = new BaseModel();
        baseModel.setModifiedTime(new Date());
        Update update = new Update();
        update.set("state", state)
                .set("modified_time", baseModel.getModifiedTime());

        return mongoTemplate.updateMulti(new Query(Criteria.where("_id").in(orderIds)), update, OrderInfo.class).getModifiedCount();
    }
}
