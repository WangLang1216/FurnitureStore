package com.summer.commonmodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 微信小程序主页数据
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeChatIndexDataBO {

    /**
     * 产品id
     */
    private String productId;

    /**
     * 名称
     */
    private String name;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 已售数量
     */
    private Integer sold;

    /**
     * 图片
     */
    private String image;

}
