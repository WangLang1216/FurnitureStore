package com.summer.securitymodule.controller;

import com.summer.commonmodule.response.ResponseEntity;
import com.summer.securitymodule.entity.vo.TokenInfoVO;
import com.summer.securitymodule.service.TokenInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * Token刷新
 * @author WangLang
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    @Resource
    private TokenInfoService tokenInfoService;

    @PostMapping("/ua/token/refresh")
    public ResponseEntity<TokenInfoVO> refreshToken(@RequestBody String refreshToken) {
        TokenInfoVO tokenInfoVO = tokenInfoService.refreshToken(refreshToken);
        return ResponseEntity.success(tokenInfoVO);
    }
}
