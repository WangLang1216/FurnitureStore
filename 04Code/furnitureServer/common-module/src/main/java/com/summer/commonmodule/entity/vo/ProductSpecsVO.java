package com.summer.commonmodule.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 产品规格信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductSpecsVO {

    /**
     * 尺寸图片
     */
    private List<String> sizeImages;

    /**
     * 尺寸
     */
    private List<String> size;

    /**
     * 颜色
     */
    private List<String> colour;

    /**
     * 规格
     */
    private List<String> materialType;

}
