package com.summer.commonmodule.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 筛选内容
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ScreenProductVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 字段
     */
    private String field;

    /**
     * 字段值
     */
    private String value;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序，0表示升序，1表示降序，-1表示不排序
     */
    private Integer sort;

}
