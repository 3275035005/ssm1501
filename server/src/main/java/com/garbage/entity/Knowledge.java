package com.garbage.entity;

import java.io.Serializable;

/**
 * knowledge
 * @author 
 */
public class Knowledge implements Serializable {
    /**
     * 序号
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}