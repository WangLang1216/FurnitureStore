package com.summer.manageserver.service;

import com.summer.manageserver.entity.vo.AdminVO;
import com.summer.manageserver.entity.vo.QueryVO;
import com.summer.manageserver.entity.vo.UserInfoVO;

import java.util.List;

/**
 * 用户信息
 * @author WangLang
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param filed 字段名
     * @param value 字段值
     * @param sysType 系统类型，1为管理普通，2为小程序，0为全部
     * @return 用户信息集合
     */
    List<UserInfoVO> getUserInfo(String filed, String value, Integer sysType);

    /**
     * 新增管理用户
     * @param adminVO 用户信息
     */
    void addAdmin(AdminVO adminVO);

    /**
     * 删除管理端用户
     * @param userIds 用户ID集合
     */
    void delUser(List<String> userIds);

    /**
     * 修改用户状态
     * @param userId 用户ID
     * @param state 状态
     * @param sysType 系统类型，true为管理端，false为小程序端
     */
    void updateUserState(String userId, Boolean state, Boolean sysType);

}
