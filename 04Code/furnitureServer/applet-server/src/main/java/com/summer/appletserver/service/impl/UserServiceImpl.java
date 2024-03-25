package com.summer.appletserver.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.summer.appletserver.entity.vo.UserInfoVO;
import com.summer.appletserver.entity.vo.UserVO;
import com.summer.appletserver.service.UserService;
import com.summer.commonmodule.entity.bo.TokenInfoBO;
import com.summer.commonmodule.entity.model.Customer;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.CustomerMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.PhoneCodeService;
import com.summer.commonmodule.utils.EncryptionUtil;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.securitymodule.common.WeChatUtil;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    private WeChatUtil weChatUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

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

        // 将图片转化为Base64内容
        String imageBase64 = null;
        try {
            byte[] imageBytes = userInfoVO.getPicture().getBytes();
            imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
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
                .setPicture(BASE64 + imageBase64);
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
    public void saveUserNickname(String token, String nickname) {
        if (CharSequenceUtil.isBlank(token) || CharSequenceUtil.isBlank(nickname)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }
        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));

        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }
        // 保存用户信息
        customer.setNickname(nickname);
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public void saveUserPicture(String token, MultipartFile image) {
        if (CharSequenceUtil.isBlank(token) || Objects.isNull(image)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));

        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        // 将图片转化为Base64内容
        String imageBase64 = null;
        try {
            byte[] imageBytes = image.getBytes();
            imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, e.getMessage(),logger);
        }

        // 保存用户信息
        customer.setPicture(BASE64 + imageBase64);
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public UserVO getUserInfo(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        UserVO userVO = new UserVO();
        userVO.setNickname(customer.getNickname())
                .setPhone(customer.getPhone())
                .setPicture(customer.getPicture())
                .setBindWeChat(Objects.nonNull(customer.getOpenId()) && !CharSequenceUtil.isBlank(customer.getOpenId()));

        return userVO;
    }

    @Override
    public void bindWeChat(String token, String code) {
        if (CharSequenceUtil.isBlank(token) || CharSequenceUtil.isBlank(code)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取OpenID
        String openId = weChatUtil.getWxOpenId(code);
        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询顾客信息
        Customer customer = customerMapper.queryCustomerById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(customer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }
        // 通过OpenID查询
        if (Objects.nonNull(customerMapper.queryCustomerByOpenId(openId))) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, "已被其他微信绑定", logger);
        }

        customer.setOpenId(openId);
        Customer saveCustomer = customerMapper.saveCustomer(customer);
        if (Objects.isNull(saveCustomer)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "数据库存储失败", logger);
        }

    }
}
