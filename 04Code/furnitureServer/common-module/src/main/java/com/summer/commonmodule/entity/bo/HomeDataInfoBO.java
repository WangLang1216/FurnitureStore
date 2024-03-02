package com.summer.commonmodule.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 微信小程序主页数据信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HomeDataInfoBO {

    /**
     * 标题
     */
    private String title;

    /**
     * 品类
     */
    private String category;

    /**
     * 显示方式，true为按热度，false为按时间最新
     */
    private Boolean showType;

}
