package com.summer.appletserver.controller;

import com.summer.appletserver.entity.vo.OrderInfoVO;
import com.summer.appletserver.entity.vo.ShoppingInfoVO;
import com.summer.appletserver.entity.vo.ShoppingUpdateVO;
import com.summer.appletserver.entity.vo.ShoppingVO;
import com.summer.appletserver.service.UserProductService;
import com.summer.commonmodule.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户产品信息
 * @author WangLang
 */
@RestController
@RequestMapping("/api/v1/user-product")
public class ProductController {


    @Autowired
    private UserProductService userProductService;

    /**
     * 查看该用户的该产品收藏状态
     * @param request 请求
     * @param productId 产品ID
     * @return Boolean
     */
    @GetMapping("/collect")
    public ResponseEntity<Boolean> getUserCollectSate(HttpServletRequest request, @RequestParam("product_id") String productId) {
        String token = request.getHeader("Authorization");
        boolean userCollectSate = userProductService.getUserCollectSate(token, productId);

        return ResponseEntity.success(userCollectSate);
    }

    /**
     * 修改该用户该产品收藏状态
     * @param request 请求
     * @param productId 产品ID
     */
    @PutMapping("/collect")
    public ResponseEntity<Void> updateUserCollectSate(HttpServletRequest request, @RequestBody String productId) {
        String token = request.getHeader("Authorization");
        userProductService.updateUserCollectSate(token, productId);

        return ResponseEntity.success();
    }

    /**
     * 新增购物车
     * @param request 请求
     * @param shoppingVO 购物车信息
     */
    @PostMapping("/shopping")
    public ResponseEntity<Void> addShopping(HttpServletRequest request, @RequestBody ShoppingVO shoppingVO) {
        String token = request.getHeader("Authorization");
        userProductService.addShopping(token, shoppingVO);

        return ResponseEntity.success();
    }

    /**
     * 获取购物车信息
     * @param request 请求
     * @return 购物车信息集合
     */
    @GetMapping("/shopping")
    public ResponseEntity<List<ShoppingInfoVO>> getShopping(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        List<ShoppingInfoVO> shoppingInfoVOS = userProductService.getShopping(token);

        return ResponseEntity.success(shoppingInfoVOS);
    }

    /**
     * 修改购物车数量
     * @param shoppingUpdateVO 购物车修改信息
     */
    @PutMapping("/shopping")
    public ResponseEntity<Void> updateShopping(@RequestBody ShoppingUpdateVO shoppingUpdateVO) {
        userProductService.updateShopping(shoppingUpdateVO);

        return ResponseEntity.success();
    }

    /**
     * 删除购物车
     * @param shoppingIds 购物ID数据集合
     */
    @DeleteMapping("/shopping")
    public ResponseEntity<Void> deleteShopping(@RequestBody String shoppingIds) {
        userProductService.deleteShopping(shoppingIds);

        return ResponseEntity.success();
    }

    /**
     * 新增订单
     * @param orderInfoVO 订单信息
     */
    @PostMapping("/order")
    public ResponseEntity<Void> addOrder(@RequestBody OrderInfoVO orderInfoVO) {
        userProductService.addOrder(orderInfoVO);

        return ResponseEntity.success();
    }

    /**
     * 新增喜好信息
     * @param request 请求
     * @param productId 产品ID
     */
    @PostMapping("/like")
    public ResponseEntity<Void> addLikeWeight(HttpServletRequest request, @RequestBody String productId) {
        String token = request.getHeader("Authorization");
        userProductService.addLikeWeight(token, productId);

        return ResponseEntity.success();
    }

}
