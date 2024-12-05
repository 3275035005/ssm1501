package com.garbage.entity;

import java.io.Serializable;

/**
 * goods
 * @author 
 */
public class Goods implements Serializable {
    /**
     * 序号
     */
    private Long id;

    /**
     * 礼品名称
     */
    private String name;

    /**
     * 礼品描述
     */
    private String description;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 库存
     */
    private Integer store;

    /**
     * 点赞数
     */
    private Integer praise;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 图片
     */
    private String image;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 礼品介绍
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}