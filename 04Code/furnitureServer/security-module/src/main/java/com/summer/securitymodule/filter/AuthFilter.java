package com.summer.securitymodule.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.securitymodule.adapter.AuthConfigAdapter;
import com.summer.securitymodule.handler.HttpHandler;
import com.summer.securitymodule.service.TokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可
 * @author WangLang
 */
@Component
public class AuthFilter implements Filter {

	@Autowired
	private AuthConfigAdapter authConfigAdapter;

	@Autowired
	private HttpHandler httpHandler;

	@Autowired
	private TokenInfoService tokenInfoService;


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 不需要授权路径
		List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();

		// 校验是否为不需要授权路径，匹配则放行
		if (CollUtil.isNotEmpty(excludePathPatterns)) {
			for (String path : excludePathPatterns) {
				AntPathMatcher pathMatcher = new AntPathMatcher();
				if (pathMatcher.match(path, request.getRequestURI())) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		// 获取Token
		String token = request.getHeader("Authorization");

		if (CharSequenceUtil.isBlank(token)) {
			httpHandler.printServerResponseToWeb(ResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
			return;
		}

		// 校验Token
		boolean verifyToken = tokenInfoService.verifyToken(token);
		if (!verifyToken) {
			httpHandler.printServerResponseToWeb(ResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
			return;
		}

		// TODO 校验权限

		chain.doFilter(request, response);
	}

}
