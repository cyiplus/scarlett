package com.cyiplus.scarlett.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 图标
     */
    private String avatar;
    /**
     * 别名
     */
    private String nicename;

    /**
     * 邮件
     */
    private String email;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 1 男，0 女
     */
    private Boolean sex;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最后一次登陆时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后一次登陆IP
     */
    private String lastLoginIp;

    /**
     * 0 未删除， 1 删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 锁
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime version;


}
