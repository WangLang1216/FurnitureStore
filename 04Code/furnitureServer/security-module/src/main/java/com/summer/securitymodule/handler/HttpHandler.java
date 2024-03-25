package com.summer.securitymodule.handler;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.commonmodule.exception.BusinessException;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.commonmodule.response.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 前端错误展示
 * @author WangLang
 */
@Component
public class HttpHandler {

	private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

	@Autowired
	private ObjectMapper objectMapper;

	public <T> void printServerResponseToWeb(ResponseEntity<T> responseEntity) {
		if (Objects.isNull(responseEntity)) {
			logger.info("print obj is null");
			return;
		}
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (Objects.isNull(requestAttributes)) {
			logger.error("requestAttributes is null, can not print to web");
			return;
		}
		HttpServletResponse response = requestAttributes.getResponse();
		if (Objects.isNull(response)) {
			logger.error("httpServletResponse is null, can not print to web");
			throw new BusinessException(ResponseEnum.UNAUTHORIZED);
		}
		logger.error("response error:{}", responseEntity.getMsg());
		response.setCharacterEncoding(CharsetUtil.UTF_8);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter printWriter;
		try {
			printWriter = response.getWriter();
			printWriter.write(objectMapper.writeValueAsString(responseEntity));
		}
		catch (IOException e) {
			throw new BusinessException("io异常：" + e);
		}
	}

}
