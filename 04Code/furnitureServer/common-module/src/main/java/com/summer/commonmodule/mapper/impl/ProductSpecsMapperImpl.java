package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.ProductSpecs;
import com.summer.commonmodule.mapper.ProductSpecsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

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

    @Override
    public ProductSpecs insertProductSpecs(ProductSpecs productSpecs) {
        Date date = new Date();
        productSpecs.setCreateTime(date)
                .setModifiedTime(date);

        return mongoTemplate.insert(productSpecs);
    }

    @Override
    public ProductSpecs saveProductSpecs(ProductSpecs productSpecs) {
        productSpecs.setModifiedTime(new Date());
        return mongoTemplate.save(productSpecs);
    }

    @Override
    public Long deleteProductSpecsById(String[] ids) {
        return mongoTemplate.remove(new Query(Criteria.where("_id").in(ids)), ProductSpecs.class).getDeletedCount();
    }
}
