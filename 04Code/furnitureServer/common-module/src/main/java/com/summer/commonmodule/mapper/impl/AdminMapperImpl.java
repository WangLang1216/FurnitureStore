package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.Admin;
import com.summer.commonmodule.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 管理平台用户信息
 * @author WangLang
 */
@Repository
public class AdminMapperImpl implements AdminMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Admin insertAdmin(Admin admin) {
        return null;
    }

    @Override
    public Admin queryAdminByAccount(String account) {
        return mongoTemplate.findOne(new Query(Criteria.where("account").is(account)), Admin.class);
    }

    @Override
    public Admin queryAdminByPhone(String phone) {
        return mongoTemplate.findOne(new Query(Criteria.where("phone").is(phone)), Admin.class);
    }
}
