package com.summer.commonmodule.mapper.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.summer.commonmodule.entity.model.Customer;
import com.summer.commonmodule.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 顾客信息管理
 * @author WangLang
 */
@Repository
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Customer insertCustomer(Customer customer) {
        customer.setUserId(IdUtil.simpleUUID());
        Date date = new Date();
        customer.setCreateTime(date)
                .setModifiedTime(date);
        return mongoTemplate.insert(customer);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setModifiedTime(new Date());
        return mongoTemplate.save(customer);
    }

    @Override
    public Customer queryCustomerById(String openId) {
        return mongoTemplate.findById(openId, Customer.class);
    }

    @Override
    public Customer queryCustomerByOpenId(String openId) {
        Query query = new Query(Criteria.where("open_id").is(openId));

        return mongoTemplate.findOne(query, Customer.class);
    }

    @Override
    public Customer queryCustomerByPhone(String phone) {
        Query query = new Query(Criteria.where("phone").is(phone));

        return mongoTemplate.findOne(query, Customer.class);
    }

    @Override
    public List<Customer> queryCustomerByRegx(String filed, String value) {
        if (CharSequenceUtil.isBlank(filed)) {
            return mongoTemplate.findAll(Customer.class);
        }

        return mongoTemplate.find(new Query(Criteria.where(filed).regex(value)), Customer.class);
    }
}
