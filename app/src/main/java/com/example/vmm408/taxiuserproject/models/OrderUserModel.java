package com.example.vmm408.taxiuserproject.models;

import java.util.Date;

public class OrderUserModel {
    private int idUserOrder;
    private String fromUserOrder;
    private String toUserOrder;
    private Date whenUserOrder;
    private double priceUserOrder;
    private String commentUserOrder;

    private UserModel userModel;
    private UserLocationModel userLocationModel;
    private UserOrderStatusModel userOrderStatusModel;

    public void setIdUserOrder(int idUserOrder) {
        this.idUserOrder = idUserOrder;
    }

    public int getIdUserOrder() {
        return idUserOrder;
    }

    public void setFromUserOrder(String fromUserOrder) {
        this.fromUserOrder = fromUserOrder;
    }

    public String getFromUserOrder() {
        return fromUserOrder;
    }

    public void setToUserOrder(String toUserOrder) {
        this.toUserOrder = toUserOrder;
    }

    public String getToUserOrder() {
        return toUserOrder;
    }

    public void setWhenUserOrder(Date whenUserOrder) {
        this.whenUserOrder = whenUserOrder;
    }

    public Date getWhenUserOrder() {
        return whenUserOrder;
    }

    public void setPriceUserOrder(double priceUserOrder) {
        this.priceUserOrder = priceUserOrder;
    }

    public double getPriceUserOrder() {
        return priceUserOrder;
    }

    public void setCommentUserOrder(String commentUserOrder) {
        this.commentUserOrder = commentUserOrder;
    }

    public String getCommentUserOrder() {
        return commentUserOrder;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserLocationModel(UserLocationModel userLocationModel) {
        this.userLocationModel = userLocationModel;
    }

    public UserLocationModel getUserLocationModel() {
        return userLocationModel;
    }

    public void setUserOrderStatusModel(UserOrderStatusModel userOrderStatusModel) {
        this.userOrderStatusModel = userOrderStatusModel;
    }

    public UserOrderStatusModel getUserOrderStatusModel() {
        return userOrderStatusModel;
    }
}
