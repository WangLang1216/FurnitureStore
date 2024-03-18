package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.Customer;

import java.util.List;

/**
 * 顾客信息管理
 * @author WangLang
 */
public interface CustomerMapper {

    /**
     * 插入顾客信息
     * @param customer 顾客信息
     * @return 顾客信息
     */
    Customer insertCustomer(Customer customer);

    /**
     * 保存已存在的顾客信息
     * @param customer 顾客信息
     * @return 顾客信息
     */
    Customer saveCustomer(Customer customer);

    /**
     * 根据ID查询顾客信息
     * @param userId 用户ID
     * @return 顾客信息
     */
    Customer queryCustomerById(String userId);

    /**
     * 根据OpenId查询顾客信息
     * @param openId 微信用户唯一ID
     * @return 顾客信息
     */
    Customer queryCustomerByOpenId(String openId);

    /**
     * 根据手机号查询顾客信息
     * @param phone 手机号
     * @return 顾客信息
     */
    Customer queryCustomerByPhone(String phone);

    /**
     * 模糊查询顾客信息
     * @param filed 字段名
     * @param value 字段值
     * @return 顾客信息集合
     */
    List<Customer> queryCustomerByRegx(String filed, String value);

}
