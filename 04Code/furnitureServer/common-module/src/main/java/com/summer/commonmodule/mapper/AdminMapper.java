package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.Admin;

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

}
