package com.summer.appletserver.service.impl;

import com.summer.appletserver.pojo.vo.CustomerVO;
import com.summer.appletserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    public String code(String phone) {
        System.out.println(phone);
        return "1234";
    }

    @Override
    public Boolean register(String phone, String password, String code) {
        logger.info("phone：" + phone + "，password：" + password + "，code：" + code);
        if ("".equals(phone) || "".equals(password) || "".equals(code)) {
            logger.error("参数为空");
            return false;
        }
        if (!"1234".equals(code)) {
            logger.error("验证码错误：" + code);
            return false;
        }

        Customer customer = new Customer();
        System.out.println(customer);
        customer.setPhone(phone).setPassword(password);
        int r = customerMapper.insert(customer);
        if (r == 0) {
            logger.info("账号：" + phone + "注册失败");
            return false;
        }
        logger.info("账号：" + phone + "注册成功");
        return true;
    }

    @Override
    public String loginByPassword(String phone, String password) {

        return null;
    }

    @Override
    public String loginByCode(String phone, String code) {
        return null;
    }

    @Override
    public CustomerVO getUserInfo(String token) {
        return null;
    }
}
