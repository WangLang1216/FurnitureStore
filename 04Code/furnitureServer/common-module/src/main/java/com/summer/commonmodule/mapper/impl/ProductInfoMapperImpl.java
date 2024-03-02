package com.summer.commonmodule.mapper.impl;

import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品信息操作合集
 * @author WangLang
 */
@Repository
public class ProductInfoMapperImpl implements ProductInfoMapper {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<ProductRecordDTO> queryProductInfo(String field, String fieldValue, String sortField, Sort.Direction direction, Integer skip, Integer limit) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("product_record")
                .localField("_id")
                .foreignField("_id")
                .as("productInfo");

        MatchOperation math = Aggregation.match(Criteria.where(field).is(fieldValue));
        SortOperation sort = Aggregation.sort(direction, sortField);
        SkipOperation skipOperation = Aggregation.skip(skip);
        LimitOperation limitOperation = Aggregation.limit(limit);
//        ProjectionOperation projection = Aggregation.project().andInclude("_id", "name", "productInfo.price", "productInfo.sold", "carousel_images");

        Aggregation aggregation = Aggregation.newAggregation(math, lookupOperation, sort, skipOperation, limitOperation);

        return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
    }
}