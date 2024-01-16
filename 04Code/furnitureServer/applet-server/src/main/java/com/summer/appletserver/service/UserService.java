package com.summer.appletserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.appletserver.pojo.entity.Customer;
import com.summer.appletserver.pojo.vo.CustomerVO;

/**
 * 用户类接口
 */
public interface UserService extends IService<Customer> {

    /**
     * 账号注册
     * @param phone
     * @param password
     * @param code
     * @return
     */
    Boolean register(String phone,  String password, String code);

    /**
     * 密码登录
     * @param phone
     * @param password
     * @return Token
     */
    String loginByPassword(String phone, String password);

    /**
     * 验证码登录
     * @param phone
     * @param code
     * @return Token
     */
    String loginByCode(String phone, String code);

    /**
     * 获取用户信息
     * @param token
     * @return CustomerVO
     */
    CustomerVO getUserInfo(String token);

}
