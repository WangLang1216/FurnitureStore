package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.ProductRecord;
import com.summer.commonmodule.mapper.ProductRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 产品记录信息
 * @author WangLang
 */
@Repository
public class ProductRecordMapperImpl implements ProductRecordMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ProductRecord queryProductRecordById(String productId) {
        return mongoTemplate.findById(productId, ProductRecord.class);
    }

    @Override
    public ProductRecord saveProductRecord(ProductRecord productRecord) {
        productRecord.setModifiedTime(new Date());
        return mongoTemplate.save(productRecord);
    }

    @Override
    public ProductRecord insertProductRecord(ProductRecord productRecord) {
        Date date = new Date();
        productRecord.setCreateTime(date)
                .setModifiedTime(date);

        return mongoTemplate.insert(productRecord);
    }

    @Override
    public Long deleteProductRecordById(String[] ids) {
        return mongoTemplate.remove(new Query(Criteria.where("_id").in(ids)), ProductRecord.class).getDeletedCount();
    }
}
