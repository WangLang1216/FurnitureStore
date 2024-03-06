package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.UserLike;
import com.summer.commonmodule.mapper.UserLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户喜好信息
 * @author WangLang
 */
@Repository
public class UserLikeMapperImpl implements UserLikeMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserLike queryUserLikeById(String ulId) {
        return mongoTemplate.findById(ulId, UserLike.class);
    }

    @Override
    public UserLike insertUserLike(UserLike userLike) {
        Date date = new Date();
        userLike.setCreateTime(date)
                .setModifiedTime(date);
        return mongoTemplate.insert(userLike);
    }

    @Override
    public UserLike saveUserLike(UserLike userLike) {
        userLike.setModifiedTime(new Date());
        return mongoTemplate.save(userLike);
    }
}
