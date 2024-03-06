package com.summer.commonmodule.mapper.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.entity.model.ProductInfo;
import com.summer.commonmodule.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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

        Aggregation aggregation = Aggregation.newAggregation(math, lookupOperation, sort, skipOperation, limitOperation);

        return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
    }

    @Override
    public List<ProductRecordDTO> queryProductInfo(String name, String field, String fieldValue, String sortField, Sort.Direction direction, Integer skip, Integer limit) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("product_record")
                .localField("_id")
                .foreignField("_id")
                .as("productInfo");

        if (Objects.isNull(name) && Objects.isNull(field) && Objects.isNull(sortField)) {
            SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "productInfo.heat");
            SkipOperation skipOperation = Aggregation.skip(skip);
            LimitOperation limitOperation = Aggregation.limit(limit);

            Aggregation aggregation = Aggregation.newAggregation(lookupOperation, sort, skipOperation, limitOperation);

            return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
        }
        if (Objects.isNull(name) && Objects.isNull(sortField)) {
            MatchOperation math = Aggregation.match(Criteria.where(field).is(fieldValue));
            SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "productInfo.heat");
            SkipOperation skipOperation = Aggregation.skip(skip);
            LimitOperation limitOperation = Aggregation.limit(limit);

            Aggregation aggregation = Aggregation.newAggregation(math, lookupOperation, sort, skipOperation, limitOperation);

            return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
        }
        if (Objects.isNull(name)) {
            MatchOperation math = Aggregation.match(Criteria.where(field).is(fieldValue));
            SortOperation sort = Aggregation.sort(direction, sortField);
            SkipOperation skipOperation = Aggregation.skip(skip);
            LimitOperation limitOperation = Aggregation.limit(limit);

            Aggregation aggregation = Aggregation.newAggregation(math, lookupOperation, sort, skipOperation, limitOperation);

            return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
        }
        MatchOperation match = Aggregation.match(Criteria.where("name").regex(name));
        if (Objects.isNull(field) && Objects.isNull(sortField)) {
            SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "productInfo.heat");
            SkipOperation skipOperation = Aggregation.skip(skip);
            LimitOperation limitOperation = Aggregation.limit(limit);

            Aggregation aggregation = Aggregation.newAggregation(match, lookupOperation, sort, skipOperation, limitOperation);
            return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
        } else if (Objects.isNull(field)) {
            SortOperation sort = Aggregation.sort(direction, sortField);
            SkipOperation skipOperation = Aggregation.skip(skip);
            LimitOperation limitOperation = Aggregation.limit(limit);

            Aggregation aggregation = Aggregation.newAggregation(match, lookupOperation, sort, skipOperation, limitOperation);

            return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
        }
        MatchOperation math = Aggregation.match(Criteria.where(field).is(fieldValue));
        SortOperation sort = Aggregation.sort(direction, sortField);
        SkipOperation skipOperation = Aggregation.skip(skip);
        LimitOperation limitOperation = Aggregation.limit(limit);

        Aggregation aggregation = Aggregation.newAggregation(match, math, lookupOperation, sort, skipOperation, limitOperation);

        return mongoTemplate.aggregate(aggregation, "product_info", ProductRecordDTO.class).getMappedResults();
    }

    @Override
    public ProductInfo queryProductInfoById(String productId) {
        return mongoTemplate.findById(productId, ProductInfo.class);
    }
}