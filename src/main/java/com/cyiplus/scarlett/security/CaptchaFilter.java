package com.cyiplus.scarlett.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyiplus.scarlett.common.exception.CaptchaException;
import com.cyiplus.scarlett.common.lang.Const;
import com.cyiplus.scarlett.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String url = request.getRequestURI();

        if ("/auth/login".equals(url) && request.getMethod().equals("POST")) {
            try {
                validate(request);
            } catch (CaptchaException e) {
                //TODO 交给认证失败处理器
                System.out.println(e);
				loginFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
        filterChain.doFilter(request, response);
    }
    // 校验验证码逻辑
	private void validate(HttpServletRequest httpServletRequest) {

		String code = httpServletRequest.getParameter("code");
		String uuid = httpServletRequest.getParameter("uuid");

		if (StringUtils.isBlank(code) || StringUtils.isBlank(uuid)) {
			throw new CaptchaException("code");
		}
		if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,uuid))) {
			throw new CaptchaException("code");
		}

		// 一次性使用
		redisUtil.hdel(Const.CAPTCHA_KEY,uuid);
	}
    
}
