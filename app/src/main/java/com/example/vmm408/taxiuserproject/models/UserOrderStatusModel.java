package com.example.vmm408.taxiuserproject.models;

public class UserOrderStatusModel {
    private int idUserOrder;
    private boolean orderAccepted;
    private boolean userInCar;

    private OrderUserModel orderUserModel;

    public void setIdUserOrder(int idUserOrder) {
        this.idUserOrder = idUserOrder;
    }

    public int getIdUserOrder() {
        return idUserOrder;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        this.orderAccepted = orderAccepted;
    }

    public boolean isOrderAccepted() {
        return orderAccepted;
    }

    public void setUserInCar(boolean userInCar) {
        this.userInCar = userInCar;
    }

    public boolean isUserInCar() {
        return userInCar;
    }

    public void setOrderUserModel(OrderUserModel orderUserModel) {
        this.orderUserModel = orderUserModel;
    }

    public OrderUserModel getOrderUserModel() {
        return orderUserModel;
    }
}
