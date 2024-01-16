package com.summer.appletserver.controller;

import com.summer.appletserver.common.response.ResponseEntity;
import com.summer.appletserver.pojo.vo.CustomerVO;
import com.summer.appletserver.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api("用户信息接口")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 账号注册
     * @param phone
     * @param password
     * @param code
     * @return Boolean
     */
    @PostMapping("/register")
    @ApiOperation(value = "账号注册")
    public ResponseEntity<Void> register(@RequestParam String phone, @RequestParam String password, @RequestParam String code) {
        boolean result = userService.register(phone, password, code);
        return result ? ResponseEntity.success() : ResponseEntity.showFailMsg("账号注册失败");
    }

    /**
     * 密码登录
     * @param phone
     * @param password
     * @return Token
     */
    @PostMapping("/login-pwd")
    @ApiOperation(value = "密码登录")
    public ResponseEntity<String> loginByPassword(@RequestParam String phone, @RequestParam String password) {
        return null;
    }

    /**
     * 验证码登录
     * @param phone
     * @param code
     * @return Token
     */
    @PostMapping("/login-code")
    @ApiOperation(value = "验证码登录")
    public ResponseEntity<String> loginByCode(@RequestParam String phone, @RequestParam String code) {
        return null;
    }

    /**
     * 获取用户信息
     * @param request
     * @return 顾客表
     */
    @GetMapping("/users")
    @ApiOperation(value = "获取账号信息")
    public ResponseEntity<CustomerVO> getUserInfo(HttpServletRequest request) {
        return null;
    }

    /**
     * 更新用户信息
     * @param customerVO
     * @return 顾客表
     */
    @PutMapping("/users")
    @ApiOperation(value = "更改账号信息")
    public ResponseEntity<CustomerVO> updateUserInfo(@RequestBody CustomerVO customerVO) {
        return null;
    }

    /**
     * 更新用户头像
     * @param picture
     * @return 图片浏览地址
     */
    @PatchMapping("/picture")
    @ApiOperation(value = "更新头像")
    public ResponseEntity<String> updatePicture(@RequestParam MultipartFile picture) {
        return null;
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return Boolean
     */
    @GetMapping("/code")
    @ApiOperation(value = "发送验证码")
    public ResponseEntity<Boolean> verification(@RequestParam String phone) {
        System.out.println("电话为：" + phone);
        return ResponseEntity.success(true);
    }
}
