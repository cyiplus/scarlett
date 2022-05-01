package com.cyiplus.scarlett.service.impl;

import com.cyiplus.scarlett.entity.SysUser;
import com.cyiplus.scarlett.mapper.SysUserMapper;
import com.cyiplus.scarlett.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

}
