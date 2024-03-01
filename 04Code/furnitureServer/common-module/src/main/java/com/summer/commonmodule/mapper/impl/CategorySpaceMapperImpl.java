package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.CategorySpace;
import com.summer.commonmodule.mapper.CategorySpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 品类信息操作
 * @author WangLang
 */
@Repository
public class CategorySpaceMapperImpl implements CategorySpaceMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CategorySpace> queryCategorySpaceAll() {
        return mongoTemplate.findAll(CategorySpace.class);
    }
}
