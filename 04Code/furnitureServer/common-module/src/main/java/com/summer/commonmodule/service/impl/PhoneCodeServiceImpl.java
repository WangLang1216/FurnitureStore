package com.summer.commonmodule.service.impl;

import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.PhoneCodeService;
import com.summer.commonmodule.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 手机验证码信息
 * @author WangLang
 */
@Service
public class PhoneCodeServiceImpl implements PhoneCodeService {

    @Autowired
    private RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(PhoneCodeServiceImpl.class);

    @Override
    public void checkCode(String phone, String code) {
        // 从Redis中校验验证码
        boolean codeKey = redisUtil.hasKey(phone);
        if (!codeKey) {
            RecordLoggerThrowException.record(ResponseEnum.INVALID_VERIFICATION_CODE, logger);
        }
        String checkCode = (String) redisUtil.get(phone);
        if (!checkCode.equals(code)) {
            RecordLoggerThrowException.record(ResponseEnum.VERIFICATION_CODE_ERROR, logger);
        }

        // 删除该验证码
        redisUtil.del(phone);
    }
}
