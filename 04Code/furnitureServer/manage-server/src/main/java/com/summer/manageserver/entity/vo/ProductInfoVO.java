package com.summer.manageserver.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * 产品信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductInfoVO {

    private String productId;
    private String name;
    private String introduce;
    private String identifier;
    private String producer;
    private String materialQuality;
    private String filler;
    private String style;
    private String category;
    private String space;
    private Integer price;
    private Integer inventory;
    private String size;
    private String colour;
    private String materialType;
    private MultipartFile[] images;
    private MultipartFile[] carouselImages;
    private MultipartFile[] physicalImages;
    private MultipartFile[] detailsImages;

}
