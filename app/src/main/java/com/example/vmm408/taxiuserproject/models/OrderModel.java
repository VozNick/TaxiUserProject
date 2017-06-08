package com.example.vmm408.taxiuserproject.models;

import java.util.Date;

public class OrderModel {
    private int idUserOrder;
    private String fromOrder;
    private String destinationOrder;
    private Date dateOrder;
    private double priceOrder;
    private String commentOrder;

    private UserModel userModel;
    private LocationModel locationModel;
    private OrderStatusModel orderStatusModel;

    public void setIdUserOrder(int idUserOrder) {
        this.idUserOrder = idUserOrder;
    }

    public int getIdUserOrder() {
        return idUserOrder;
    }

    public void setFromOrder(String fromOrder) {
        this.fromOrder = fromOrder;
    }

    public String getFromOrder() {
        return fromOrder;
    }

    public void setDestinationOrder(String destinationOrder) {
        this.destinationOrder = destinationOrder;
    }

    public String getDestinationOrder() {
        return destinationOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setPriceOrder(double priceOrder) {
        this.priceOrder = priceOrder;
    }

    public double getPriceOrder() {
        return priceOrder;
    }

    public void setCommentOrder(String commentOrder) {
        this.commentOrder = commentOrder;
    }

    public String getCommentOrder() {
        return commentOrder;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setOrderStatusModel(OrderStatusModel orderStatusModel) {
        this.orderStatusModel = orderStatusModel;
    }

    public OrderStatusModel getOrderStatusModel() {
        return orderStatusModel;
    }
}
