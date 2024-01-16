package com.summer.appletserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.summer.appletserver.pojo.entity.Customer;
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
