package com.summer.appletserver.controller;

import com.summer.appletserver.entity.vo.UserInfoVO;
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
@RequestMapping("/api/v1/ua/user")
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

        // TODO 测试
//        String fileName = picture.getOriginalFilename();
//        java.io.File dest = new java.io.File("C:\\Users\\WangLang\\Desktop\\新建文件夹" + "/" + fileName);
//        try (java.io.OutputStream outputStream = new java.io.FileOutputStream(dest)){
//            byte[] bytes = picture.getBytes();
//            outputStream.write(bytes);
//        } catch (Exception e){
//            throw new RuntimeException("文件保存失败", e);
//        }


        return ResponseEntity.success();
    }

    /**
     * 更改用户手机号信息
     * @param request 请求
     * @param phoneCodeVO 手机号验证码信息
     */
    @PutMapping("/phone")
    public ResponseEntity<Void> saveUserPhone(HttpServletRequest request, PhoneCodeVO phoneCodeVO) {
        String token = request.getHeader("Authorization");
        userService.saveUserPhone(token, phoneCodeVO);

        return ResponseEntity.success();
    }

    /**
     * 用户绑定微信
     * @param request 请求
     * @param code 微信凭证
     */
    @PutMapping("/bind-wx")
    public ResponseEntity<Void> bindUserWeChat(HttpServletRequest request, String code) {
        String token = request.getHeader("Authorization");
        userService.bindUserWeChat(token, code);

        return ResponseEntity.success();
    }

}
