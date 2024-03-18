package com.summer.manageserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 查询信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QueryVO {

    /**
     * 字段
     */
    private String filed;

    /**
     * 字段值
     */
    private String value;

    /**
     * 页码
     */
    private Integer page;

}
