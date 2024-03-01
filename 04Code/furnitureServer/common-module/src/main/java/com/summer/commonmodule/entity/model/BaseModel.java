package com.summer.commonmodule.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 时间类
 * @author WangLang
 */
@Data
@ToString
@Accessors(chain = true)
public class BaseModel implements Serializable {

    /**
     * 创建时间
     */
    @Field("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 更新时间
     */
    @Field("modified_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date modifiedTime;

}
