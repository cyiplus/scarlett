package com.cyiplus.scarlett.security;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.SysRecord;
import com.cyiplus.scarlett.service.SysRecordService;
import com.cyiplus.scarlett.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import cn.hutool.json.JSONUtil;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SysRecordService sysRecordService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        
        // sys login record - Fail
        SysRecord sysRecord = new SysRecord();
        sysRecord.setCategory("Login");
        sysRecord.setKeyword("Fail");
        sysRecord.setIp(IPUtils.getIpAddr(request));
        sysRecord.setCreateTime(LocalDateTime.now());
        // sysRecord.setUser(iUser.getUsername());
        sysRecordService.Record(sysRecord);

        Result result = Result.fail("用户名或密码错误");
        // TODO 删掉上线之前 System.out.println(exception.getMessage());
        System.out.println(exception.getMessage());
        if (exception.getMessage().equals("code")) {
            result = Result.fail("验证码错误!");
        }
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        
        outputStream.flush();
        outputStream.close();
        
    }
    
}
