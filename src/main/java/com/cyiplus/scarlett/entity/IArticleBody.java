package com.cyiplus.scarlett.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class IArticleBody implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 文章编码
     */
    private String articleId;

    /**
     * 内容
     */
    private String content;


}
