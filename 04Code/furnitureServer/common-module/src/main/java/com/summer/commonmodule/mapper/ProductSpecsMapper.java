package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.ProductSpecs;

/**
 * 产品规格信息
 * @author WangLang
 */
public interface ProductSpecsMapper {

    /**
     * 根据ID获取产品规格信息
     * @param productId 产品ID
     * @return 产品规格信息
     */
    ProductSpecs queryProductSpecsById(String productId);



}
