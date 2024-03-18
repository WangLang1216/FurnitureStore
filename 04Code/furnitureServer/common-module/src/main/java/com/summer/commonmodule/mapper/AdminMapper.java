package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.Admin;

import java.util.List;

/**
 * 管理平台用户信息
 * @author WangLang
 */
public interface AdminMapper {

    /**
     * 新增管理账号
     * @param admin 管理平台用户信息
     * @return 用户信息
     */
    Admin insertAdmin(Admin admin);

    /**
     * 保存管理账号
     * @param admin 管理平台用户信息
     * @return 用户信息
     */
    Admin saveAdmin(Admin admin);

    /**
     * 根据账号查询管理平台用户信息
     * @param account 账号
     * @return 用户信息
     */
    Admin queryAdminByAccount(String account);

    /**
     * 根据手机号查询管理平台用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    Admin queryAdminByPhone(String phone);

    /**
     * 模糊查询管理平台用户信息
     * @param filed 字段名
     * @param value 字段值
     * @return 用户信息集合
     */
    List<Admin> queryAdminByRegx(String filed, String value);

    /**
     * 根据ID删除管理用户
     * @param userIds 用户ID
     * @return 删除数量
     */
    Long removeAdminByIds(String[] userIds);

}
