package com.summer.appletserver.service;

import com.summer.appletserver.entity.vo.OrderInfoVO;
import com.summer.appletserver.entity.vo.ShoppingInfoVO;
import com.summer.appletserver.entity.vo.ShoppingUpdateVO;
import com.summer.appletserver.entity.vo.ShoppingVO;

import java.util.List;

/**
 * 用户产品信息
 * @author WangLang
 */
public interface UserProductService {

    /**
     * 获取该用户该产品收藏状态
     * @param token 令牌
     * @param productId 产品ID
     * @return Boolean
     */
    Boolean getUserCollectSate(String token, String productId);

    /**
     * 修改该用户该产品收藏状态
     * @param token 令牌
     * @param productId 产品ID
     */
    void updateUserCollectSate(String token, String productId);

    /**
     * 新增购物车
     * @param token 令牌
     * @param shoppingVO 购物车信息
     */
    void addShopping(String token, ShoppingVO shoppingVO);

    /**
     * 获取购物车信息
     * @param token 令牌
     * @return 购物车信息集合
     */
    List<ShoppingInfoVO> getShopping(String token);

    /**
     * 修改购物车数量
     * @param shoppingUpdateVO 购物车修改信息
     */
    void updateShopping(ShoppingUpdateVO shoppingUpdateVO);

    /**
     * 删除购物车
     * @param shoppingIds 购物ID集合
     */
    void deleteShopping(String shoppingIds);

    /**
     * 新增订单
     * @param orderInfoVO 订单信息
     */
    void addOrder(OrderInfoVO orderInfoVO);

    /**
     * 修改喜好信息
     * @param token 令牌
     * @param productId 产品ID
     */
    void addLikeWeight(String token, String productId);
}
