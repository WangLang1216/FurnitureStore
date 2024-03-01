package com.summer.commonmodule.entity.vo;

import com.summer.commonmodule.entity.bo.CategorySpaceInfoBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 品类空间信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CategorySpaceVO {

    /**
     * 类型，分为品类（category）和空间（space）
     */
    private String type;

    /**
     * 信息
     */
    List<CategorySpaceInfoBO> spaceInfo;

}
