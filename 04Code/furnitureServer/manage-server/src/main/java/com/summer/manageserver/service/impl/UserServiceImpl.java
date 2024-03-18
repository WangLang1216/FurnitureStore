package com.summer.manageserver.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.summer.commonmodule.entity.model.Admin;
import com.summer.commonmodule.entity.model.Customer;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.AdminMapper;
import com.summer.commonmodule.mapper.CustomerMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.manageserver.entity.bo.UserInfoBO;
import com.summer.manageserver.entity.vo.AdminVO;
import com.summer.manageserver.entity.vo.UserInfoVO;
import com.summer.manageserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息
 * @author WangLang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<UserInfoVO> getUserInfo(String filed, String value, Integer sysType) {
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        UserInfoVO adminUserInfoVO = new UserInfoVO();
        List<Admin> admins = adminMapper.queryAdminByRegx(filed, value);
        List<UserInfoBO> adminUserInfoBOS = new ArrayList<>();
        for (Admin admin : admins) {
            UserInfoBO userInfoBO = new UserInfoBO();
            userInfoBO.setUserId(admin.getUserId())
                    .setName(admin.getAccount())
                    .setRole(admin.getRole())
                    .setPhone(admin.getPhone())
                    .setState(admin.getState())
                    .setCreateTime(admin.getCreateTime());
            adminUserInfoBOS.add(userInfoBO);
        }
        adminUserInfoVO.setSysType(1)
                .setUserInfoBOS(adminUserInfoBOS);
        userInfoVOS.add(adminUserInfoVO);


        List<Customer> customers = customerMapper.queryCustomerByRegx(filed, value);
        UserInfoVO customerUserInfoVO = new UserInfoVO();
        List<UserInfoBO> customerUserInfoBOS = new ArrayList<>();
        for (Customer customer : customers) {
            UserInfoBO userInfoBO = new UserInfoBO();
            userInfoBO.setUserId(customer.getUserId())
                    .setName(customer.getNickname())
                    .setPhone(customer.getPhone())
                    .setState(customer.getState())
                    .setCreateTime(customer.getCreateTime());
            customerUserInfoBOS.add(userInfoBO);
        }
        customerUserInfoVO.setSysType(2)
                .setUserInfoBOS(customerUserInfoBOS);
        userInfoVOS.add(customerUserInfoVO);

        if (sysType == 0) {
            return userInfoVOS;
        }

        if (sysType == 1) {
            userInfoVOS.remove(1);
            return userInfoVOS;
        }
        userInfoVOS.remove(0);
        return userInfoVOS;

    }

    @Override
    public void addAdmin(AdminVO adminVO) {
        if (Objects.isNull(adminVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 查询该账号
        Admin adminByAccount = adminMapper.queryAdminByAccount(adminVO.getAccount());
        if (Objects.nonNull(adminByAccount)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "已存在该账号", logger);
        }

        // 查询该手机号
        Admin queryAdminByPhone = adminMapper.queryAdminByPhone(adminVO.getPhone());
        if (Objects.nonNull(queryAdminByPhone)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "手机号已被其他账号使用", logger);
        }

        Admin admin = new Admin();
        admin.setAccount(adminVO.getAccount())
                .setPassword(passwordEncoder.encode(adminVO.getPassword()))
                .setPhone(adminVO.getPhone());

        Admin insertAdmin = adminMapper.insertAdmin(admin);
        if (Objects.isNull(insertAdmin)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public void delUser(List<String> userIds) {
        if (userIds.isEmpty()){
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        String[] split = userIds.toString().substring(1, userIds.toString().length() - 1).split(StrPool.COMMA + CharSequenceUtil.SPACE);

        long delCount = adminMapper.removeAdminByIds(split);
        if ((int) delCount == 0) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public void updateUserState(String userId, Boolean state, Boolean sysType) {
        if (CharSequenceUtil.isBlank(userId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        if (Boolean.TRUE.equals(sysType)) {
            List<Admin> admins = adminMapper.queryAdminByRegx("_id", userId);
            if (admins.isEmpty()) {
                RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "为查询到该用户", logger);
            }
            Admin admin = admins.get(0);
            admin.setState(state);
            Admin saveAdmin = adminMapper.saveAdmin(admin);
            if (Objects.isNull(saveAdmin)) {
                RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
            }
            return;
        }

        Customer customer = customerMapper.queryCustomerById(userId);
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "为查询到该用户", logger);
        }
        customer.setState(state);
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }
}
