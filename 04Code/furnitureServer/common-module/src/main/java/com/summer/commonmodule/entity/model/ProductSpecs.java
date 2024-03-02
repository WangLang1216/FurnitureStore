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
 * 产品规格表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("product_spaces")
public class ProductSpecs extends BaseModel implements Serializable {

    @Id
    private String productId;

    /**
     * 尺寸
     */
    @Field("size")
    private List<String> size;

    /**
     * 颜色
     */
    @Field("colour")
    private List<String> colour;

    /**
     * 材质
     */
    @Field("material_type")
    private List<String> materialType;

    /**
     * 图片
     */
    @Field("images")
    private List<Image> images;

}
