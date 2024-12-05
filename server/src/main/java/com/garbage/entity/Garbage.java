package com.garbage.entity;

import java.io.Serializable;

/**
 * garbage
 * @author
 */
public class Garbage implements Serializable {
    private Long id;

    /**
     * 垃圾名称
     */
    private String title;

    /**
     * 垃圾类型（0可回收垃圾，1有害垃圾，2厨余垃圾，3其他垃圾）
     */
    private String type;

    /**
     * 图片
     */
    private String image;

    /**
     * 匹配度
     */
    private Float matched;

    /**
     * 审核状态（0未审核 1已审核）
     */
    private String status;

    /**
     * 上传用户id
     */
    private Long userId;

    /**
     * 奖励积分
     */
    private Integer point;

    /**
     * 评价内容
     */
    private String evaluate;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     *  用户头像
     */
    private String userImage;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getMatched() {
        return matched;
    }

    public void setMatched(Float matched) {
        this.matched = matched;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
