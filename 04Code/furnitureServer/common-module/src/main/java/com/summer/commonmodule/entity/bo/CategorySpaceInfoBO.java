package com.summer.commonmodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 品类空间信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CategorySpaceInfoBO {

    /**
     * ID
     */
    private String csId;
    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 热搜值
     */
    private String heat;
}
