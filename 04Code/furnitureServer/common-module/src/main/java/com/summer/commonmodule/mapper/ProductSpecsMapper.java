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

    /**
     * 新增产品规格信息
     * @param productSpecs 产品规格信息
     * @return 产品规格
     */
    ProductSpecs insertProductSpecs(ProductSpecs productSpecs);

    /**
     * 保存产品规格信息
     * @param productSpecs 产品规格信息
     * @return 产品规格
     */
    ProductSpecs saveProductSpecs(ProductSpecs productSpecs);

    /**
     * 根据ID删除产品规格信息
     * @param ids 产品规格ID
     * @return 删除数量
     */
    Long deleteProductSpecsById(String[] ids);
}
