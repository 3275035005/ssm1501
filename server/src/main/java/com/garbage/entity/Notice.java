package com.garbage.entity;

import java.io.Serializable;

/**
 * notice
 * @author
 */
public class Notice implements Serializable {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     *图片
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
