package com.garbage.entity;

import java.io.Serializable;

/**
 * order
 * @author
 */
public class Order implements Serializable {
    /**
     * 序号
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 积分
     */
    private Integer totalPoint;

    /**
     * 下单人id
     */
    private Long userId;

    /**
     * 状态 0待付款 1已支付 2 订单完成
     */
    private String status;

    /**
     * 礼品id
     */
    private Long goodsId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 礼品名称
     */
    private String goodsName;

    /**
     * 用户姓名
     */
    private String userName;

    private String image;

    private String number;
    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价类型（1 好评 2中评 3差评）
     */
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
