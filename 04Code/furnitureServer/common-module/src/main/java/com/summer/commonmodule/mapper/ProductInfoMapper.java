package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.entity.model.ProductInfo;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 产品信息操作合集
 * @author WangLang
 */
public interface ProductInfoMapper {

    /**
     * 查询product_info表和product_record表数据，通过字段和排序
     * @param field 字段名称
     * @param fieldValue 字段值
     * @param sortField 排序字段名称
     * @param direction 排序顺序
     * @param skip 跳过
     * @param limit 页数
     * @return product_info表和product_record表数据集合
     */
    List<ProductRecordDTO> queryProductInfo(String field, String fieldValue, String sortField, Sort.Direction direction, Integer skip, Integer limit);

    /**
     * 查询product_info表和product_record表数据，通过字段和排序
     * @param name 名称
     * @param field 字段名称
     * @param fieldValue 字段值
     * @param sortField 排序字段名称
     * @param direction 排序顺序
     * @param skip 跳过
     * @param limit 页数
     * @return product_info表和product_record表数据集合
     */
    List<ProductRecordDTO> queryProductInfo(String name, String field, String fieldValue, String sortField, Sort.Direction direction, Integer skip, Integer limit);

    /**
     * 根据ID查询产品信息
     * @param productId 产品ID
     * @return 产品信息
     */
    ProductInfo queryProductInfoById(String productId);

}
