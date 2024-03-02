package com.summer.commonmodule.entity.vo;

import com.summer.commonmodule.entity.bo.WeChatIndexDataBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 微信小程序主页数据
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeChatIndexDataVO {

    /**
     * 标题
     */
    private String title;

    /**
     * 商品集合
     */
    private List<WeChatIndexDataBO> commodity;

}
