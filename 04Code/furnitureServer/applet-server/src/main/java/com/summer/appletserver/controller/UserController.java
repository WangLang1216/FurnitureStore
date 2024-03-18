package com.summer.appletserver.controller;

import com.summer.appletserver.entity.vo.UserInfoVO;
import com.summer.appletserver.entity.vo.UserVO;
import com.summer.appletserver.service.UserService;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息
 * @author WangLang
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存用户信息
     * @param request 请求
     * @param nickname 昵称
     * @param picture 头像
     */
    @PostMapping()
    public ResponseEntity<Void> saveUserInfo(HttpServletRequest request, @RequestParam("nickname") String nickname,
                                             @RequestParam("picture") MultipartFile picture) {
        String token = request.getHeader("Authorization");
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setNickname(nickname)
                .setPicture(picture);

        userService.saveUserInfo(token, userInfoVO);

        return ResponseEntity.success();
    }

    /**
     * 更改用户手机号信息
     * @param request 请求
     * @param phoneCodeVO 手机号验证码信息
     */
    @PutMapping("/phone")
    public ResponseEntity<Void> saveUserPhone(HttpServletRequest request, @RequestBody PhoneCodeVO phoneCodeVO) {
        String token = request.getHeader("Authorization");
        userService.saveUserPhone(token, phoneCodeVO);

        return ResponseEntity.success();
    }

    /**
     * 更改用户昵称信息
     * @param request 请求
     * @param nickname 昵称
     */
    @PutMapping("/nickname")
    public ResponseEntity<Void> saveUserNickname(HttpServletRequest request, @RequestBody String nickname) {
        String token = request.getHeader("Authorization");
        userService.saveUserNickname(token, nickname);

        return ResponseEntity.success();
    }

    /**
     * 更改用户头像
     * @param request 请求
     * @param picture 头像
     */
    @PostMapping("/picture")
    public ResponseEntity<Void> saveUserPicture(HttpServletRequest request, @RequestParam("picture") MultipartFile picture) {
        String token = request.getHeader("Authorization");
        userService.saveUserPicture(token, picture);

        return ResponseEntity.success();
    }

    /**
     * 获取用户信息
     * @param request 请求
     * @return 用户信息
     */
    @GetMapping()
    public ResponseEntity<UserVO> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserVO userInfo = userService.getUserInfo(token);

        return ResponseEntity.success(userInfo);
    }

    @GetMapping("/bind/we-chat")
    public ResponseEntity<Void> bindWeChat(HttpServletRequest request, @RequestParam String code) {
        String token = request.getHeader("Authorization");
        userService.bindWeChat(token, code);

        return ResponseEntity.success();
    }

}
