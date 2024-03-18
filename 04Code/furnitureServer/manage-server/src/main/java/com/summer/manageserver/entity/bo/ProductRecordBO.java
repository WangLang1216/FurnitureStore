package com.summer.manageserver.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 产品记录信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductRecordBO {

    private String productId;
    private String name;
    private String identifier;
    private String materialQuality;
    private String filler;
    private String style;
    private String category;
    private String space;
    private Integer price;
    private Integer inventory;
    private Integer heat;
    private String image;
    private Double score;
    private Date creationTime;

}
