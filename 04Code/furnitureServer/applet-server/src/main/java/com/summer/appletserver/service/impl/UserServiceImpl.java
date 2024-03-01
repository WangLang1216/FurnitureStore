package com.summer.appletserver.service.impl;

import com.summer.appletserver.entity.vo.UserInfoVO;
import com.summer.appletserver.service.UserService;
import com.summer.commonmodule.entity.model.Customer;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.CustomerMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.PhoneCodeService;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.securitymodule.common.EncryptionUtil;
import com.summer.securitymodule.common.WeChatUtil;
import com.summer.securitymodule.entity.bo.TokenInfoBO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
    private PhoneCodeService phoneCodeService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private WeChatUtil weChatUtil;

    /**
     * 头像图片使用base64位
     */
    private static final String BASE64 = "data:image/png;base64,";

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void saveUserInfo(String token, UserInfoVO userInfoVO) {
        if (Objects.isNull(userInfoVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 将图片转化为二进制内容
        byte[] bytes = {};
        try {
            bytes = Files.readAllBytes(Paths.get(Objects.requireNonNull(userInfoVO.getPicture().getOriginalFilename())));
        } catch (IOException e) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, e.getMessage(),logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));

        // 从数据库中查询用户信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        customer.setNickname(userInfoVO.getNickname())
                .setPicture(BASE64 + Arrays.toString(bytes));
        // 保存用户信息
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public void saveUserPhone(String token, PhoneCodeVO phoneCodeVO) {
        if (Objects.isNull(phoneCodeVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 校验验证码
        phoneCodeService.checkCode(phoneCodeVO.getPhone(), phoneCodeVO.getCode());

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));

        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }
        // 保存用户信息
        customer.setPhone(phoneCodeVO.getPhone());
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public void bindUserWeChat(String token, String code) {
        // 获取微信OpenId
        String openId = weChatUtil.getWxOpenId(code);

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));

        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }
        // 保存用户信息
        customer.setOpenId(openId);
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }
}
