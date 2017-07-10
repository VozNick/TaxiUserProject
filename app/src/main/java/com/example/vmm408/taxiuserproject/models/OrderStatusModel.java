package com.example.vmm408.taxiuserproject.models;

public class OrderStatusModel {
    private String idUserOrderStatus;
    private boolean orderAcceptedStatus;
//    private boolean userInCarStatus;

    private OrderModel orderModel;

    public void setIdUserOrderStatus(String idUserOrderStatus) {
        this.idUserOrderStatus = idUserOrderStatus;
    }

    public String getIdUserOrderStatus() {
        return idUserOrderStatus;
    }

    public void setOrderAcceptedStatus(boolean orderAcceptedStatus) {
        this.orderAcceptedStatus = orderAcceptedStatus;
    }

    public boolean isOrderAcceptedStatus() {
        return orderAcceptedStatus;
    }

//    public void setUserInCarStatus(boolean userInCarStatus) {
//        this.userInCarStatus = userInCarStatus;
//    }
//
//    public boolean isUserInCarStatus() {
//        return userInCarStatus;
//    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public static class OrderStatus {
        private static OrderStatusModel orderStatusModel = new OrderStatusModel();

        public static void setOrderStatusModel(OrderStatusModel orderStatusModel) {
            OrderStatus.orderStatusModel = orderStatusModel;
        }

        public static OrderStatusModel getOrderStatusModel() {
            return orderStatusModel;
        }
    }
}
