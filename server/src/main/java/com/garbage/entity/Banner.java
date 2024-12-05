package com.garbage.entity;

import java.io.Serializable;

/**
 * banner
 * @author
 */
public class Banner implements Serializable {
    /**
     * 序号
     */
    private Long id;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 关联url
     */
    private String url;

    private String remark;

    private String createTime;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
