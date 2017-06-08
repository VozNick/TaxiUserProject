package com.example.vmm408.taxiuserproject.models;

public class OrderStatusModel {
    private int idUserOrderStatus;
    private boolean orderAcceptedStatus;
    private boolean userInCarStatus;

    private OrderModel orderModel;

    public void setIdUserOrderStatus(int idUserOrderStatus) {
        this.idUserOrderStatus = idUserOrderStatus;
    }

    public int getIdUserOrderStatus() {
        return idUserOrderStatus;
    }

    public void setOrderAcceptedStatus(boolean orderAcceptedStatus) {
        this.orderAcceptedStatus = orderAcceptedStatus;
    }

    public boolean isOrderAcceptedStatus() {
        return orderAcceptedStatus;
    }

    public void setUserInCarStatus(boolean userInCarStatus) {
        this.userInCarStatus = userInCarStatus;
    }

    public boolean isUserInCarStatus() {
        return userInCarStatus;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }
}
