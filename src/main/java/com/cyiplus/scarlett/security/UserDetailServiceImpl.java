package com.cyiplus.scarlett.security;

import java.util.List;
import com.cyiplus.scarlett.entity.SysUser;
import com.cyiplus.scarlett.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser iUser = sysUserService.getUserByUsername(username);
        if (iUser == null) {
            throw new UsernameNotFoundException(username + ":用户不存在");
        }
        return new AuthUser(iUser.getId(),iUser.getUsername(), iUser.getPassword(), getUserAuthority(iUser.getId()));
    }
    
    /**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param Long
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(String userId){
		// 角色(ROLE_admin)、菜单操作权限 sys:user:list
		// String authority = iUserService.getUserAuthorityInfo(userId);
		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_Admin");
	}
    
}