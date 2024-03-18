package com.summer.commonmodule.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.PhoneCodeService;
import com.summer.commonmodule.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 手机验证码信息
 * @author WangLang
 */
@Service
public class PhoneCodeServiceImpl implements PhoneCodeService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 过期时间
     */
    private static final Integer CODE_EXPIRE = 300;
    /**
     * 签名
     */
    private static final String SIGN_NAME = "松柏家具";
    /**
     * 模板
     */
    private static final String TEMPLATE_CODE = "SMS_465332346";
    /**
     * 阿里云短信配置信息
     */
    @Value("${aliyun.accessKey}")
    private String accessKey;
    @Value("${aliyun.secret}")
    private String secret;
    /**
     * 服务地区
     */
    private static final String REGION_ID = "cn-hangzhou";
    /**
     * 短信API产品名称，固定
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 短信API产品的域名，固定
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final Logger logger = LoggerFactory.getLogger(PhoneCodeServiceImpl.class);

    @Override
    public void checkCode(String phone, String code) {
        if (CharSequenceUtil.isBlank(phone) || CharSequenceUtil.isBlank(code)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }
        // 从Redis中校验验证码
        boolean codeKey = redisUtil.hasKey(phone);
        if (!codeKey) {
            RecordLoggerThrowException.record(ResponseEnum.INVALID_VERIFICATION_CODE, logger);
        }
        Integer checkCode = (Integer) redisUtil.get(phone);
        if (!checkCode.toString().equals(code)) {
            RecordLoggerThrowException.record(ResponseEnum.VERIFICATION_CODE_ERROR, logger);
        }

        // 删除该验证码
        redisUtil.del(phone);
    }

    @Override
    public void sendSMS(String phone) {
        if (CharSequenceUtil.isBlank(phone)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }
        // 随机数验证码
        Random random = new Random();
        int code = random.nextInt(90000) + 10000;

        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKey, secret);
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            IAcsClient client = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phone);
            request.setSignName(SIGN_NAME);
            request.setTemplateCode(TEMPLATE_CODE);
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            SendSmsResponse response = client.getAcsResponse(request);
            logger.info(response.getCode(), response.getMessage());
            logger.info("发送短信验证码成功{}验证码为{}", phone, code);
        } catch (ClientException e) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "验证码发送失败" + e, logger);
        }
        // 存储在Redis中
        boolean phoneKey = redisUtil.hasKey(phone);
        if (phoneKey) {
            redisUtil.del(phone);
        }
        boolean setPhoneKey = redisUtil.set(phone, code, CODE_EXPIRE);
        if (!setPhoneKey) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "存储验证码失败" + phone, logger);;
        }

    }
}
