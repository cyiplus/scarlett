package com.cyiplus.scarlett.config;

import com.cyiplus.scarlett.security.CaptchaFilter;
import com.cyiplus.scarlett.security.JwtAccessDeniedHandler;
import com.cyiplus.scarlett.security.JwtAuthenticationEntryPoint;
import com.cyiplus.scarlett.security.JwtAuthenticationFilter;
import com.cyiplus.scarlett.security.LoginFailureHandler;
import com.cyiplus.scarlett.security.LoginSuccessHandler;
import com.cyiplus.scarlett.security.TokenLogoutSuccessHandler;
import com.cyiplus.scarlett.security.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
	private CaptchaFilter captchaFilter;
    
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    TokenLogoutSuccessHandler tokenLogoutSuccessHandler;
    

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		return jwtAuthenticationFilter;
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl);
    }

    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()

            .formLogin()
            // .loginPage("/login")
            .loginProcessingUrl("/auth/login")
            .failureHandler(loginFailureHandler)
            .successHandler(loginSuccessHandler)

            .and()
            .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessHandler(tokenLogoutSuccessHandler)

            // 禁用session
			// .and()
			// .sessionManagement()
			// .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 异常处理器
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// 配置拦截规则
            .and()
            .authorizeRequests()
            // .antMatchers(URL_WHITELIST).permitAll()
            .antMatchers("/auth/captcha","/auth/login","/login","/home","/view","/","/assets/**","/api/**").permitAll()
            // .antMatchers("/auth/**","login.html","/api/**").permitAll()
            .anyRequest().authenticated()

            // 配置自定义的过滤器
			.and()
			.addFilter(jwtAuthenticationFilter())
			.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)

            ;
    }
}
