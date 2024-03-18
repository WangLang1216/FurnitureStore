package com.summer.manageserver.service;

import com.summer.manageserver.entity.vo.ProductHeatOrSalesVO;
import com.summer.manageserver.entity.vo.ProductInfoVO;
import com.summer.manageserver.entity.vo.ProductRecordVO;
import com.summer.manageserver.entity.vo.ShopSalesVO;

import java.util.List;

/**
 * 产品信息
 * @author WangLang
 */
public interface ProductInfoService {

    /**
     * 查询产品热度或销量榜单
     * @param type 类型，heat表示热度，sales表示销量
     * @return 产品热度/销售信息
     */
    ProductHeatOrSalesVO getProductHeatOrSales(String type);

    /**
     * 新增产品信息
     * @param productInfoVO 产品信息
     */
    void addProductInfo(ProductInfoVO productInfoVO);

    /**
     * 查询产品记录信息
     * @param field 字段
     * @param value 字段值
     * @param page 页码
     * @return 产品记录信息集合
     */
    ProductRecordVO getProductRecord(String field, String value, Integer page);

    /**
     * 删除产品信息
     * @param productId 产品ID集合
     */
    void delProductInfo(List<String> productId);

    /**
     * 根据ID查询产品信息
     * @param productId 产品ID
     * @return 产品信息
     */
    ProductInfoVO getProductById(String productId);

    /**
     * 修改产品信息
     * @param productInfoVO 产品信息
     */
    void updateProductInfo(ProductInfoVO productInfoVO);

}
