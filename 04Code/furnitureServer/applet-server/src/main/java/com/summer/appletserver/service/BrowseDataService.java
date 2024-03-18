package com.summer.appletserver.service;

import com.summer.commonmodule.entity.bo.WeChatIndexDataBO;
import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.entity.vo.ProductInfoVO;
import com.summer.commonmodule.entity.vo.ProductSpecsVO;
import com.summer.commonmodule.entity.vo.WeChatIndexDataVO;

import java.util.List;

/**
 * 浏览数据信息管理
 * @author WangLang
 */
public interface BrowseDataService {

    /**
     * 获取品类空间信息
     * @return 品类空间信息集合，信息包含在CategorySpaceVO中的List<CategorySpaceInfoBO>中
     */
    List<CategorySpaceVO> getCategorySpaceInfo();

    /**
     * 获取微信小程序主页数据
     * @return 微信小程序主页数据信息集合
     */
    List<WeChatIndexDataVO> getWeChatIndexData();

    /**
     * 根据产品ID查询产品详情信息
     * @param productId 产品ID
     * @return 产品详情信息
     */
    ProductInfoVO getProductInfo(String productId);

    /**
     * 根据ID获取产品规格信息
     * @param productId 产品ID
     * @return 产品规格信息
     */
    ProductSpecsVO getProductSpecs(String productId);

    /**
     * 更新产品热度
     * @param productId 产品ID
     * @param number 数量
     */
    void updateProductHeat(String productId, Integer number);

    /**
     * 根据字段和值进行查询和排序
     * @param name 产品名称
     * @param field 字段名
     * @param value 字段值
     * @param sortField 排序字段
     * @param sort 排序值，0表示升序，1表示降序，-1表示不排序
     * @return 产品信息集合
     */
    List<WeChatIndexDataBO> getScreenProduct(String name, String field, String value, String sortField, Integer sort);

    /**
     * 个性化推荐
     * @param token 用户令牌
     * @return 产品信息集合
     */
    List<WeChatIndexDataBO> getUserLikeProduct(String token);

}
