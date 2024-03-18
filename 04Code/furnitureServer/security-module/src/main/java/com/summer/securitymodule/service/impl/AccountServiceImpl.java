package com.summer.securitymodule.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.entity.bo.TokenInfoBO;
import com.summer.commonmodule.entity.bo.UserInfoTokenBO;
import com.summer.commonmodule.entity.model.Admin;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.AdminMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.PhoneCodeService;
import com.summer.commonmodule.utils.EncryptionUtil;
import com.summer.commonmodule.utils.RedisUtil;
import com.summer.securitymodule.common.WeChatUtil;
import com.summer.securitymodule.common.TokenUtil;
import com.summer.securitymodule.constant.SysTypeEnum;
import com.summer.securitymodule.entity.bo.StoreTokenInfoBO;
import com.summer.commonmodule.entity.model.Customer;
import com.summer.securitymodule.entity.bo.UserTokenBO;
import com.summer.securitymodule.entity.vo.AccountVO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import com.summer.securitymodule.entity.vo.TokenInfoVO;
import com.summer.commonmodule.mapper.CustomerMapper;
import com.summer.securitymodule.service.AccountService;
import com.summer.securitymodule.service.TokenInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 账号登录-登出
 * @author WangLang
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CustomerMapper accountMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private TokenInfoService tokenInfoService;

    @Autowired
    private PhoneCodeService phoneCodeService;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public TokenInfoVO login(String code) {
        if (CharSequenceUtil.isBlank(code)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取微信用户OpenId
        String openId = weChatUtil.getWxOpenId(code);
        // 是否为初次登录
        boolean firstLogin = false;

        // 查询数据库中是否存在该用户，不存在则插入
        Customer customer = accountMapper.queryCustomerByOpenId(openId);
        if (Objects.nonNull(customer)) {
            // 校验是否设置过昵称和头像，未设置则默认首次登录
            if (Objects.isNull(customer.getNickname()) && Objects.isNull(customer.getPicture())) {
                firstLogin = true;
            }
        }
        if (Objects.isNull(customer)) {
            Customer insertCustomer = new Customer();
            insertCustomer.setOpenId(openId);
            Customer insert = accountMapper.insertCustomer(insertCustomer);
            if (Objects.isNull(insert)) {
                RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
            }
            customer = insert;
            // 设置为首次登录
            firstLogin = true;
        }

        // 存储Token信息
        StoreTokenInfoBO storeTokenInfoBO = new StoreTokenInfoBO();
        storeTokenInfoBO.setUserId(customer.getUserId())
                .setSysType(SysTypeEnum.APPLET.value())
                .setState(customer.getState())
                .setFirstLogin(firstLogin);

        return storeTokenInfo(storeTokenInfoBO);
    }

    @Override
    public TokenInfoVO login(AccountVO accountVO) {
        if (Objects.isNull(accountVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 根据账号查询用户信息
        Admin admin = adminMapper.queryAdminByAccount(accountVO.getAccount());
        if (Objects.isNull(admin)) {
            RecordLoggerThrowException.record(ResponseEnum.USERINFO_IS_EMPTY, accountVO.getAccount(), logger);
        }
        // 解密密码匹配
        boolean matches = passwordEncoder.matches(accountVO.getPassword(), admin.getPassword());
        if (!matches) {
            RecordLoggerThrowException.record(ResponseEnum.PASSWORD_ERROR, accountVO.getAccount(), logger);
        }

        // 存储Token信息
        StoreTokenInfoBO storeTokenInfoBO = new StoreTokenInfoBO();
        storeTokenInfoBO.setUserId(admin.getUserId())
                .setSysType(SysTypeEnum.MANAGE.value())
                .setState(admin.getState());

        return storeTokenInfo(storeTokenInfoBO);
    }

    @Override
    public TokenInfoVO login(PhoneCodeVO phoneCodeVO) {
        if (Objects.isNull(phoneCodeVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        String phone = phoneCodeVO.getPhone();

        // 校验Key是否存在
        boolean existKey = redisUtil.hasKey(phone);
        if (!existKey) {
            RecordLoggerThrowException.record(ResponseEnum.INVALID_VERIFICATION_CODE, logger);
        }

        // 通过手机号Key查询验证码值
        Integer code = (Integer) redisUtil.get(phone);
        if (!code.toString().equals(phoneCodeVO.getCode())) {
            RecordLoggerThrowException.record(ResponseEnum.VERIFICATION_CODE_ERROR, logger);
        }

        // 是否为初次登录
        boolean firstLogin = false;

        StoreTokenInfoBO storeTokenInfoBO = new StoreTokenInfoBO();

        // 判断是微信小程序端还是Web管理平台端
        if (phoneCodeVO.getSysType()) {
            Customer customer = accountMapper.queryCustomerByPhone(phone);
            if (Objects.nonNull(customer)) {
                // 校验是否设置过昵称和头像，未设置则默认首次登录
                if (Objects.isNull(customer.getNickname()) && Objects.isNull(customer.getPicture())) {
                    firstLogin = true;
                }
            }
            // 查询数据库中是否存在该用户，不存在则插入
            if (Objects.isNull(customer)) {
                    Customer insertCustomer = new Customer();
                    insertCustomer.setPhone(phone);
                    Customer insert = accountMapper.insertCustomer(insertCustomer);
                    if (Objects.isNull(insert)) {
                        RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
                    }
                    customer = insert;
                    // 设置为首次登录
                    firstLogin = true;
            }
            // 存储Token信息
            storeTokenInfoBO.setUserId(customer.getUserId())
                    .setSysType(SysTypeEnum.APPLET.value())
                    .setState(customer.getState())
                    .setFirstLogin(firstLogin);
        } else {
            Admin admin = adminMapper.queryAdminByPhone(phone);
            // 管理平台用户必须有账号
            if (Objects.isNull(admin)) {
                RecordLoggerThrowException.record(ResponseEnum.USER_REJECTED, phone, logger);
            }
            // 存储Token信息
            storeTokenInfoBO.setUserId(admin.getUserId())
                    .setSysType(SysTypeEnum.MANAGE.value())
                    .setState(admin.getState());
        }

        // 从Redis中删除短信验证码
        redisUtil.del(phone);

        return storeTokenInfo(storeTokenInfoBO);
    }

    @Override
    public void sendSmsCode(String phone) {
        if (CharSequenceUtil.isBlank(phone)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        phoneCodeService.sendSMS(phone);

    }

    @Override
    public void logout(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的用户信息
        TokenInfoBO tokenInfo = (TokenInfoBO) tokenUtil.getTokenInfo(token);

        // 查询Redis中的该用户Token信息
        UserTokenBO userTokenBO = (UserTokenBO) redisUtil.get(tokenInfo.getUserInfoTokenBO().getUserId());
        // 删除该用户Token信息
        redisUtil.del(userTokenBO.getAccessToken(), userTokenBO.getRefreshToken(), tokenInfo.getUserInfoTokenBO().getUserId());
    }

    /**
     * 存储Token信息
     * @param storeTokenInfoBO 用户信息
     * @return TokenInfoVO
     */
    private TokenInfoVO storeTokenInfo(StoreTokenInfoBO storeTokenInfoBO) {
        // 校验该用户状态是否正常
        if (!storeTokenInfoBO.getState()) {
            RecordLoggerThrowException.record(ResponseEnum.USER_REJECTED, storeTokenInfoBO.getUserId(), logger);
        }

        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setFirstLogin(storeTokenInfoBO.getFirstLogin());

        UserInfoTokenBO userInfoTokenBO = new UserInfoTokenBO();
        userInfoTokenBO.setUserId(storeTokenInfoBO.getUserId());
        userInfoTokenBO.setSysType(storeTokenInfoBO.getSysType());
        // 创建并存储Token
        TokenInfoBO tokenInfoBO = tokenInfoService.createAndStoreToken(userInfoTokenBO);

        BeanUtils.copyProperties(tokenInfoBO, tokenInfoVO);
        // 加密Token
        tokenInfoVO.setAccessToken(encryptionUtil.privateKeyEncryption(tokenInfoVO.getAccessToken()))
                .setRefreshToken(encryptionUtil.privateKeyEncryption(tokenInfoVO.getRefreshToken()));

        return tokenInfoVO;
    }

}
