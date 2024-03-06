package com.summer.commonmodule.entity.vo;

import com.summer.commonmodule.entity.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * 产品信息详情
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductInfoVO {

    @Id
    private String productId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品介绍
     */
    private String introduce;

    /**
     * 商品编号
     */
    private String identifier;

    /**
     * 工厂编号
     */
    private String factoryNumber;

    /**
     * 产地
     */
    private String producer;

    /**
     * 材质
     */
    private String materialQuality;

    /**
     * 填充物
     */
    private String filler;

    /**
     * 包件
     */
    private String piece;

    /**
     * 工艺
     */
    private String technology;

    /**
     * 安装方式
     */
    private String installationMethod;

    /**
     * 风格
     */
    private String style;

    /**
     * 品类
     */
    private String category;

    /**
     * 空间
     */
    private String space;

    /**
     * 货期
     */
    private String term;

    /**
     * 服务
     */
    private String service;

    /**
     * 轮播图
     */
    private List<Image> carouselImages;

    /**
     * 实拍图
     */
    private List<Image> physicalImages;

    /**
     * 详情图
     */
    private List<Image> detailsImages;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 已售数量
     */
    private Integer sold;

    /**
     * 热度
     */
    private Integer heat;

}
