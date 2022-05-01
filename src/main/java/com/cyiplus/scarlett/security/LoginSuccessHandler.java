package com.cyiplus.scarlett.security;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyiplus.scarlett.common.lang.Const;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.SysRecord;
import com.cyiplus.scarlett.entity.SysUser;
import com.cyiplus.scarlett.service.SysRecordService;
import com.cyiplus.scarlett.service.SysUserService;
import com.cyiplus.scarlett.util.IPUtils;
import com.cyiplus.scarlett.util.JwtTokenUtil;
import com.cyiplus.scarlett.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SysUserService iUserService;

    @Autowired
    private SysRecordService sysRecordService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt，并放置到请求头中
		String jwt = jwtTokenUtil.generateToken(authentication.getName());
		response.setHeader(jwtTokenUtil.getHeader(), jwt);

    //update user profile for last_login_ip and last_login_time
    SysUser iUser = iUserService.getOne(new QueryWrapper<SysUser>().eq("username", authentication.getName()));
    iUser.setLastLoginIp(IPUtils.getIpAddr(request));
    iUser.setLastLoginTime(LocalDateTime.now());
    iUserService.updateById(iUser);

    // sys login record - success
    SysRecord sysRecord = new SysRecord();
    sysRecord.setCategory("Login");
    sysRecord.setKeyword("Success");
    sysRecord.setIp(IPUtils.getIpAddr(request));
    sysRecord.setCreateTime(LocalDateTime.now());
    sysRecord.setUser(iUser.getUsername());
    sysRecordService.Record(sysRecord);

    if (!redisUtil.hHasKey(Const.LOGIN_KEY, iUser.getUsername())) {
      redisUtil.hset(Const.LOGIN_KEY, iUser.getUsername(), iUser.getLastLoginIp(), 10*60);
      // redisUtil.hset(Const.LOGIN_KEY, iUser.getLastLoginIp(), iUser.getUsername(), 10*60);
    }

		Result result =  Result.succ(
            MapUtil.builder()
                    .put("Token", jwt)
                    .put("Admin", iUser)
                    .build()
    );

		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

		outputStream.flush();
		outputStream.close();
        

    }
    
}
