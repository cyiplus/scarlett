package com.cyiplus.scarlett.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 类型
     */
    private String category;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 用户
     */
    private String user;

    /**
     * 文章编码
     */
    private String articleId;

    /**
     * 备注
     */
    private String remark;

    /**
     * IP
     */
    private String ip;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
