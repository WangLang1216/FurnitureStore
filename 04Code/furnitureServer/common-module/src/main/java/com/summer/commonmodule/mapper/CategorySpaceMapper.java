package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.CategorySpace;

import java.util.List;

/**
 * 品类信息表操作
 * @author WangLang
 */
public interface CategorySpaceMapper {

    /**
     * 查询全部品类空间信息
     * @return 空间信息集合
     */
    List<CategorySpace> queryCategorySpaceAll();

}
