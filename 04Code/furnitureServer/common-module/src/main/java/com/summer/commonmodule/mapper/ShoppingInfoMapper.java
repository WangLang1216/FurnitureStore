package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.ShoppingInfo;

import java.util.List;

/**
 * 购物车信息
 * @author WangLang
 */
public interface ShoppingInfoMapper {

    /**
     * 新增购物车信息
     * @param shoppingInfo 购物车信息
     * @return 购物车信息
     */
    ShoppingInfo insertShoppingInfo(ShoppingInfo shoppingInfo);

    /**
     * 保存购物车信息
     * @param shoppingInfo 购物车信息
     * @return 购物车信息
     */
    ShoppingInfo saveShoppingInfo(ShoppingInfo shoppingInfo);

    /**
     * 根据用户ID查询购物车信息
     * @param userId 用户ID
     * @return 购物车信息集合
     */
    List<ShoppingInfo> queryShoppingInfoByUserId(String userId);

    /**
     * 根据ID查询购物车信息
     * @param shoppingId 购物车ID
     * @return 购物车信息
     */
    ShoppingInfo queryShoppingById(String shoppingId);

    /**
     * 根据ID集合查询购物车信息
     * @param shoppingIds 购物车ID集合
     * @return 购物车信息集合
     */
    List<ShoppingInfo> queryShoppingListById(String shoppingIds);

    /**
     * 根据ID删除购物车信息
     * @param shoppingIds 购物车ID
     * @return 删除数量
     */
    Long deleteShoppingByIds(String shoppingIds);

}
