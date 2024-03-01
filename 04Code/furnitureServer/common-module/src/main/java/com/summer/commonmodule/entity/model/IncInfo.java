package com.summer.commonmodule.entity.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * ID自增进度表
 * @author WangLang
 */
@Data
@ToString
@Accessors(chain = true)
@Document("inc")
public class IncInfo extends BaseModel implements Serializable {

    @Id
    private String id;

    /**
     * 集合名称
     */
    @Field("collection_name")
    private String collectionName;

    /**
     * 当前自增ID
     */
    @Field("inc_id")
    private Integer incId;

}
