package com.summer.appletserver.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.summer.appletserver.common.response.ResponseEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api("用户信息接口")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/wx")
public class WxController {

    @GetMapping("/code")
    public ResponseEntity<String> getWxOpenId(String code) {
        System.out.println("code=" + code);
        String appId = "wxc491e906e319a6f0";
        String secret = "d5cc494b26125284ea40dd4c1042fa29";
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appId + "&secret=" + secret + "&js_code=" + code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(result);
        String openId = jsonObject.getString("openid");
        System.out.println(openId);
        return ResponseEntity.success(openId);
    }


}
