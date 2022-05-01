package com.cyiplus.scarlett.service.impl;

import com.cyiplus.scarlett.entity.SysLogin;
import com.cyiplus.scarlett.mapper.SysLoginMapper;
import com.cyiplus.scarlett.service.SysLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-22
 */
@Service
public class SysLoginServiceImpl extends ServiceImpl<SysLoginMapper, SysLogin> implements SysLoginService {

    @Override
    public void recordLogin(SysLogin sysLogin) {
       save(sysLogin);
        
    }

}
