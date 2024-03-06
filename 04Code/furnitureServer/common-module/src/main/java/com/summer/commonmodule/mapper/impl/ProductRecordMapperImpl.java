package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.model.ProductRecord;
import com.summer.commonmodule.mapper.ProductRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
