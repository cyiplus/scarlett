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
public class IComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 文章编码
     */
    private String article_id;

    /**
     * 父编码
     */
    private String parentId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 0 未发布， 1 发布
     */
    private Boolean publish;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;


}
