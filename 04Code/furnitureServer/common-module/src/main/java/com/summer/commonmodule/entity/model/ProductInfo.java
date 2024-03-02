package com.summer.commonmodule.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * 产品信息表，包含默认值信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("product_info")
public class ProductInfo extends BaseModel implements Serializable {

    @Id
    private String productId;

    /**
     * 产品名称
     */
    @Field("name")
    private String name;

    /**
     * 产品介绍
     */
    @Field("introduce")
    private String introduce;

    /**
     * 商品编号
     */
    @Field("identifier")
    private String identifier;

    /**
     * 工厂编号
     */
    @Field("factory_number")
    private String factoryNumber;

    /**
     * 产地
     */
    @Field("producer")
    private String producer;

    /**
     * 材质
     */
    @Field("material_quality")
    private String materialQuality;

    /**
     * 填充物
     */
    @Field("filler")
    private String filler = "其他";

    /**
     * 包件
     */
    @Field("piece")
    private String piece = "1件";

    /**
     * 工艺
     */
    @Field("technology")
    private String technology = "其他";

    /**
     * 安装方式
     */
    @Field("installation_method")
    private String installationMethod = "组装";

    /**
     * 风格
     */
    @Field("style")
    private String style;

    /**
     * 品类
     */
    @Field("category")
    private String category;

    /**
     * 空间
     */
    @Field("space")
    private String space;

    /**
     * 货期
     */
    @Field("term")
    private String term = "15天";

    /**
     * 服务
     */
    @Field("service")
    private String service = "包送包安装";

    /**
     * 轮播图
     */
    @Field("carousel_images")
    private List<Image> carouselImages;

    /**
     * 实拍图
     */
    @Field("physical_images")
    private List<Image> physicalImages;

    /**
     * 详情图
     */
    @Field("details_images")
    private List<Image> detailsImages;

}
