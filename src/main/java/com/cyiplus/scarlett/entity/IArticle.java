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
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IArticle implements Serializable {

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
     * 主题
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 副标题是否带图片 0 没有， 1张图片，3张图片
     */
    private Integer titleImages;

    /**
     * 副标题图片链接
     */
    private String titleImagesUrl;

    /**
     * 类型
     */
    private String category;

    /**
     * 0 未发布， 1 已经发布
     */
    private Boolean publish;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 查看人数
     */
    private Integer views;

    /**
     * 点赞人数
     */
    private Integer likes;

    /**
     * 评论次数
     */
    private Integer comments;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeTime;

    /**
     * 锁
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime version;


    @TableField(exist = false)
    private String bodyId;

    @TableField(exist = false)
    private String body;

    @TableField(exist = false)
    private SysUser author;

}
