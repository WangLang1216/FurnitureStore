package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.ProductRecord;

/**
 * 产品记录信息
 * @author WangLang
 */
public interface ProductRecordMapper {

    /**
     * 根据产品ID获取产品记录信息
     * @param productId 产品ID
     * @return 产品记录信息
     */
    ProductRecord queryProductRecordById(String productId);

    /**
     * 修改产品记录信息
     * @param productRecord 产品记录信息
     * @return 产品记录信息
     */
    ProductRecord saveProductRecord(ProductRecord productRecord);

    /**
     * 新增产品记录信息
     * @param productRecord 产品记录信息
     * @return 产品记录信息
     */
    ProductRecord insertProductRecord(ProductRecord productRecord);

    /**
     * 根据ID删除产品记录信息
     * @param ids 产品记录ID
     * @return 删除数量
     */
    Long deleteProductRecordById(String[] ids);

}
