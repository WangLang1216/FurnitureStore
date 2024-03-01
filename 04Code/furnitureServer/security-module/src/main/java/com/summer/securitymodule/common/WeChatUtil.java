package com.summer.securitymodule.common;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.response.ResponseEnum;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取微信OpenID
 * @author WangLang
 */
@Data
@Component
public class WeChatUtil {

    @Value("${weChat.appId}")
    private String APP_ID;

    @Value("${weChat.secret}")
    private String SECRET;

    private static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 获取微信用户唯一ID
     * @param code 操作码
     * @return OpenId
     */
    public String getWxOpenId(String code) {
//        //TODO 暂时
//        return "123";
        JSONObject jsonCode = JSON.parseObject(code);
        String authUrl = AUTH_URL + "&appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + jsonCode.get("code");
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        System.out.println(openid);
        if (CharSequenceUtil.isBlank(openid)) {
            RecordLoggerThrowException.record(ResponseEnum.OPEN_ID_ACQUISITION_FAILED, logger);
        }
        return jsonObject.getString("openid");
    }

}
