package com.summer.securitymodule.controller;

import com.summer.commonmodule.response.ResponseEntity;
import com.summer.securitymodule.entity.vo.AccountVO;
import com.summer.securitymodule.entity.vo.PhoneCodeVO;
import com.summer.securitymodule.entity.vo.TokenInfoVO;
import com.summer.securitymodule.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 账号登录-登出-短信
 * @author WangLang
 */
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Resource
    private AccountService accountService;

    /**
     * 微信小程序用户使用微信登录登录
     * @param code 用户唯一码
     * @return Token信息
     */
    @GetMapping("/ua/wx/login")
    public ResponseEntity<TokenInfoVO> login(@RequestParam @NotBlank String code) {
        TokenInfoVO tokenInfoVO = accountService.login(code);

        return ResponseEntity.success(tokenInfoVO);
    }

    /**
     * 管理平台用户登录
     * @param accountVO 账号信息
     * @return Token信息
     */
    @PostMapping("/ua/web/login")
    public ResponseEntity<TokenInfoVO> login(@NotNull AccountVO accountVO) {
        TokenInfoVO tokenInfoVO = accountService.login(accountVO);

        return ResponseEntity.success(tokenInfoVO);
    }

    /**
     * 手机号验证码登录
     * @param phoneCodeVO 手机验证码信息
     * @return Token信息
     */
    @PostMapping("/ua/phone/login")
    public ResponseEntity<TokenInfoVO> login(@RequestBody @NotNull PhoneCodeVO phoneCodeVO) {
        TokenInfoVO tokenInfoVO = accountService.login(phoneCodeVO);

        return ResponseEntity.success(tokenInfoVO);
    }

    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    @GetMapping("/ua/sms")
    public ResponseEntity<Void> sendSmsCode(@RequestParam @NotBlank String phone) {
        accountService.sendSmsCode(phone);

        return ResponseEntity.success();
    }

    /**
     * 退出登录
     * @param request 请求
     */
    @DeleteMapping("/log-out")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        accountService.logout(token);

        return ResponseEntity.success();
    }

}
