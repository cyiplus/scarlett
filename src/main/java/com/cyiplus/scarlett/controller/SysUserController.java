package com.cyiplus.scarlett.controller;

import java.security.Principal;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.SysUser;
import com.cyiplus.scarlett.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-22
 */
@RestController
public class SysUserController {

    @Autowired
    SysUserService sysUserService;
    //all
    @GetMapping("/sys/users")
    // @RequestMapping(value = "/sys/users",method = RequestMethod.GET,consumes = "application/json;charset=UTF-8")
    public Result list(@RequestParam(value = "username", required = false) String username,
    @RequestParam(value = "nickname", required = false) String nickname,
    @RequestParam(value = "email", required = false) String email,
    Principal principal) {

        List<SysUser> sysUsers = sysUserService.list(new QueryWrapper<SysUser>().eq(username!=null, "username", username)
        .eq(nickname != null, "nickname", nickname).eq(email != null, "email", email));
        return Result.succ(sysUsers);
    }

    //put

    //add

    //delete

    //single row

}
