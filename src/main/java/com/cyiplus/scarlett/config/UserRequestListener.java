package com.cyiplus.scarlett.config;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import com.cyiplus.scarlett.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class UserRequestListener implements ServletRequestListener {
    
    @Autowired
    RedisUtil redisUtil;

    // @Override
    // public void requestInitialized(ServletRequestEvent servletRequestEvent){
    //     System.out.println("requestInitialized");
    // }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent){

        Object principal = servletRequestEvent.getServletRequest().getServletContext();

        // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // if (!redisUtil.hHasKey(Const.LOGIN_KEY, iUser.getUsername())) {
        //     redisUtil.hset(Const.LOGIN_KEY, iUser.getUsername(), iUser.getLastLoginIp(), 10*60);
        //     // redisUtil.hset(Const.LOGIN_KEY, iUser.getLastLoginIp(), iUser.getUsername(), 10*60);
        //   }
        // System.out.println("principal");
        // System.out.println(principal);
    }

}
