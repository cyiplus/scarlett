package com.cyiplus.scarlett.service;

import com.cyiplus.scarlett.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-22
 */
public interface SysUserService extends IService<SysUser> {
// 根据用户名获取adm_user表的一条数据
public SysUser getUserByUsername(String username);
}
