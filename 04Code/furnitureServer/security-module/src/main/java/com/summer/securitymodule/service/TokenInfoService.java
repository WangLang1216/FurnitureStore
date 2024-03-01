package com.summer.securitymodule.service;

import com.summer.securitymodule.entity.bo.TokenInfoBO;
import com.summer.securitymodule.entity.bo.UserInfoTokenBO;
import com.summer.securitymodule.entity.vo.TokenInfoVO;

/**
 * Token信息管理
 * @author WangLang
 */
public interface TokenInfoService {

    /**
     * 创建并存储Token信息
     * @param userInfoTokenBO 用户Token信息
     * @return TokenInfoBO
     */
    TokenInfoBO createAndStoreToken(UserInfoTokenBO userInfoTokenBO);

    /**
     * 校验Token
     * @param token 令牌
     * @return Boolean
     */
    Boolean verifyToken(String token);

    /**
     * 刷新Token
     * @param refreshToken 令牌
     * @return TokenInfoVO
     */
    TokenInfoVO refreshToken(String refreshToken);

}
