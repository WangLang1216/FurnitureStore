package com.summer.commonmodule.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 喜好信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LikeProduct {

    /**
     * 品类名称
     */
    private String category;

    /**
     * 权重
     */
    private Integer weight;
}
