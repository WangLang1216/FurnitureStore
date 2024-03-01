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

/**
 * 品类信息表
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document("category_space")
public class CategorySpace extends BaseModel implements Serializable {

    @Id
    private String csId;

    /**
     * 名称
     */
    @Field("name")
    private String name;

    /**
     * 图片
     */
    @Field("image")
    private String image;

    /**
     * 图片路径
     */
    @Field("image_path")
    private String imagePath;

    /**
     * 类型，分为品类（category）和空间（space）
     */
    @Field("type")
    private String type;

    /**
     * 热搜值
     */
    @Field("heat")
    private String heat;

}
