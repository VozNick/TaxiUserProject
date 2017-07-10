package com.example.vmm408.taxiuserproject.models;

import java.util.Date;

public class OrderModel {
    private String idUserOrder;
    private String fromOrder;
    private String destinationOrder;
    private double priceOrder;
    private String commentOrder;

    private UserModel userModel;
    private LocationModel locationModel;
    private OrderStatusModel orderStatusModel;

    public void setIdUserOrder(String idUserOrder) {
        this.idUserOrder = idUserOrder;
    }

    public String getIdUserOrder() {
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

    public static class Order {
        private static OrderModel orderModel = new OrderModel();

        public static void setOrderModel(OrderModel orderModel) {
            Order.orderModel = orderModel;
        }

        public static OrderModel getOrderModel() {
            return orderModel;
        }
    }
}
