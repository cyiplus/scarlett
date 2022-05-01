package com.cyiplus.scarlett.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户编码
     */
    private String userId;
    /**
     * 用户编码
     */
    private String username;
    /**
     * 登陆时间
     */
    private LocalDateTime loginTime;

    /**
     * 登陆IP
     */
    private String loginIp;

    /**
     * 登陆平台
     */
    private String loginClient;


}
