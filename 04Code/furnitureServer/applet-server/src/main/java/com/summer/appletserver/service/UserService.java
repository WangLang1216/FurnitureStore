package com.summer.appletserver.service;

import com.summer.appletserver.entity.vo.UserInfoVO;
import com.summer.appletserver.entity.vo.UserVO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息
 * @author WangLang
 */
public interface UserService {

    /**
     *  保存用户信息
     * @param token 令牌
     * @param userInfoVO 用户信息
     */
    void saveUserInfo(String token, UserInfoVO userInfoVO);

    /**
     * 更改用户手机号信息
     * @param token 令牌
     * @param phoneCodeVO 手机号验证码信息
     */
    void saveUserPhone(String token, PhoneCodeVO phoneCodeVO);

    /**
     * 更改用户昵称信息
     * @param token 令牌
     * @param nickname 昵称
     */
    void saveUserNickname(String token, String nickname);

    /**
     * 更改用户头像
     * @param token 令牌
     * @param image 头像
     */
    void saveUserPicture(String token, MultipartFile image);

    /**
     * 获取用户信息
     * @param token 令牌
     * @return 用户信息
     */
    UserVO getUserInfo(String token);
}
