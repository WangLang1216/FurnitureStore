package com.summer.commonmodule.controller;

import com.summer.commonmodule.entity.bo.WeChatIndexDataBO;
import com.summer.commonmodule.entity.vo.*;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.commonmodule.service.BrowseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 浏览数据信息
 * @author WangLang
 */
@RestController
@RequestMapping("/api/v1/ua")
public class BrowseDataController {

    @Autowired
    private BrowseDataService browseDataService;

    /**
     * 获取品类空间信息
     * @return 品类空间信息集合
     */
    @GetMapping("/category-spaceInfo")
    public ResponseEntity<List<CategorySpaceVO>> getCategorySpaceInfo() {
        List<CategorySpaceVO> categorySpaceVOS = browseDataService.getCategorySpaceInfo();

        return ResponseEntity.success(categorySpaceVOS);
    }

    /**
     * 获取微信小程序主页数据
     * @return 微信小程序主页数据信息集合
     */
    @GetMapping("/we-chat/index")
    public ResponseEntity<List<WeChatIndexDataVO>> getWeChatIndexData() {
        List<WeChatIndexDataVO> weChatIndexDataVOS = browseDataService.getWeChatIndexData();

        return ResponseEntity.success(weChatIndexDataVOS);
    }

    /**
     * 根据产品ID查询产品详情信息
     * @param productId 产品ID
     * @return 产品详情信息
     */
    @GetMapping("/product-info")
    public ResponseEntity<ProductInfoVO> getProductInfo(@RequestParam("product_id") String productId) {
        ProductInfoVO productInfoVO = browseDataService.getProductInfo(productId);

        return ResponseEntity.success(productInfoVO);
    }

    /**
     * 根据ID获取产品规格信息
     * @param productId 产品ID
     * @return 产品规格信息
     */
    @GetMapping("/product-specs")
    public ResponseEntity<ProductSpecsVO> getProductSpecs(@RequestParam("product_id") String productId) {
        ProductSpecsVO productSpecsVO = browseDataService.getProductSpecs(productId);

        return ResponseEntity.success(productSpecsVO);
    }

    /**
     * 修改产品热度值
     * @param productId 产品ID
     * @param number 数量，与前端约定熟成，浏览增加10，收藏增加30，加入购物车增加50
     */
    @GetMapping("/product-heat")
    public ResponseEntity<Void> updateProductHeat(@RequestParam("product_id") String productId, @RequestParam("number") Integer number) {
        browseDataService.updateProductHeat(productId, number);

        return ResponseEntity.success();
    }

    /**
     * 根据字段和值进行查询和排序
     * @param screenProductVO 筛选内容
     * @return 产品信息集合
     */
    @PostMapping("/product-screen")
    public ResponseEntity<List<WeChatIndexDataBO>> getScreenProduct(@RequestBody ScreenProductVO screenProductVO) {
        List<WeChatIndexDataBO> weChatIndexDataBOS = browseDataService.getScreenProduct(screenProductVO.getName(),
                screenProductVO.getField(), screenProductVO.getValue(), screenProductVO.getSortField(), screenProductVO.getSort());

        return ResponseEntity.success(weChatIndexDataBOS);
    }

    /**
     * 个性化推荐
     * @param token 用户令牌
     * @return 产品信息集合
     */
    @GetMapping("/product-like")
    public ResponseEntity<List<WeChatIndexDataBO>> getUserLikeProduct(@RequestParam("token") String token) {
        List<WeChatIndexDataBO> weChatIndexDataBOS = browseDataService.getUserLikeProduct(token);

        return ResponseEntity.success(weChatIndexDataBOS);
    }


}
