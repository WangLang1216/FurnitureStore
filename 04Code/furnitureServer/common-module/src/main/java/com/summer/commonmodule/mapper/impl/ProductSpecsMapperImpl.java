package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.ProductSpecs;
import com.summer.commonmodule.mapper.ProductSpecsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * 产品规格信息
 * @author WangLang
 */
@Repository
public class ProductSpecsMapperImpl implements ProductSpecsMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ProductSpecs queryProductSpecsById(String productId) {
        return mongoTemplate.findById(productId, ProductSpecs.class);
    }
}
