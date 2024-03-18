package com.summer.manageserver.entity.vo;

import com.summer.manageserver.entity.bo.ProductRecordBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 产品信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductRecordVO {

    private List<ProductRecordBO> productRecordBOS;

    private Integer page;

    private Integer total;

}
