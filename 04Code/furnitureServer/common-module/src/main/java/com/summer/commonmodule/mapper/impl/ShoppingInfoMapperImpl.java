package com.summer.commonmodule.mapper.impl;

import cn.hutool.core.util.IdUtil;
import com.summer.commonmodule.entity.model.ShoppingInfo;
import com.summer.commonmodule.mapper.ShoppingInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物车信息
 */
@Repository
public class ShoppingInfoMapperImpl implements ShoppingInfoMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ShoppingInfo insertShoppingInfo(ShoppingInfo shoppingInfo) {
        Date date = new Date();
        shoppingInfo.setShoppingId(IdUtil.simpleUUID())
                .setCreateTime(date)
                .setModifiedTime(date);
        return mongoTemplate.insert(shoppingInfo);
    }

    @Override
    public ShoppingInfo saveShoppingInfo(ShoppingInfo shoppingInfo) {
        shoppingInfo.setModifiedTime(new Date());
        return mongoTemplate.save(shoppingInfo);
    }

    @Override
    public List<ShoppingInfo> queryShoppingInfoByUserId(String userId) {
        return mongoTemplate.find(new Query(Criteria.where("user_id").is(userId)).with(Sort.by(Sort.Order.desc("create_time"))), ShoppingInfo.class);
    }

    @Override
    public ShoppingInfo queryShoppingById(String shoppingId) {
        return mongoTemplate.findById(shoppingId, ShoppingInfo.class);
    }

    @Override
    public List<ShoppingInfo> queryShoppingListById(String[] shoppingIds) {
        return mongoTemplate.find(new Query(Criteria.where("_id").in(shoppingIds)), ShoppingInfo.class);
    }

    @Override
    public Long deleteShoppingByIds(String[] shoppingIds) {
        return mongoTemplate.remove(new Query(Criteria.where("_id").in(shoppingIds)), ShoppingInfo.class).getDeletedCount();
    }

    @Override
    public Long deleteShoppingByProductIds(String[] productIds) {
        return mongoTemplate.remove(new Query(Criteria.where("product_id").in(productIds)), ShoppingInfo.class).getDeletedCount();
    }
}
