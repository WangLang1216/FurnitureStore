package com.summer.commonmodule.mapper.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.summer.commonmodule.entity.model.Admin;
import com.summer.commonmodule.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
        Date date = new Date();
        admin.setUserId(IdUtil.simpleUUID())
             .setRole(0)
             .setState(true)
             .setCreateTime(date)
             .setModifiedTime(date);

        return mongoTemplate.insert(admin);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        admin.setModifiedTime(new Date());

        return mongoTemplate.save(admin);
    }

    @Override
    public Admin queryAdminByAccount(String account) {
        return mongoTemplate.findOne(new Query(Criteria.where("account").is(account)), Admin.class);
    }

    @Override
    public Admin queryAdminByPhone(String phone) {
        return mongoTemplate.findOne(new Query(Criteria.where("phone").is(phone)), Admin.class);
    }

    @Override
    public List<Admin> queryAdminByRegx(String filed, String value) {
        if (CharSequenceUtil.isBlank(filed)) {
            return mongoTemplate.find(new Query().with(Sort.by(Sort.Direction.ASC, "create_time")), Admin.class);
        }

        return mongoTemplate.find(new Query(Criteria.where(filed).regex(value)).with(Sort.by(Sort.Direction.DESC, "create_time")), Admin.class);
    }

    @Override
    public Long removeAdminByIds(String[] userIds) {
        return mongoTemplate.remove(new Query(Criteria.where("_id").in(userIds)), Admin.class).getDeletedCount();
    }
}
