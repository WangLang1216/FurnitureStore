package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 产品信息操作合集
 * @author WangLang
 */
public interface ProductInfoMapper {


    List<ProductRecordDTO> queryProductInfo(String field, String fieldValue, String sortField, Sort.Direction direction, Integer skip, Integer limit);



}
