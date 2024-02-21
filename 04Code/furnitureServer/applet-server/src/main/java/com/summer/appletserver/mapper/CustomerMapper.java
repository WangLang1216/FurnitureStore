package com.summer.appletserver.mapper;

import org.springframework.stereotype.Component;

@Component("customerMapper")
public interface CustomerMapper extends BaseMapper<Customer> {

//    /**
//     * 账号注册
//     * @param phone
//     * @param password
//     * @return Integer
//     */
//    Integer register(String phone, String password);

}
