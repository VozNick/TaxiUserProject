package com.example.vmm408.taxiuserproject.models;

import java.util.Date;

public class OrderModel {
    private String idUserOrder;
    private String fromOrder;
    private String destinationOrder;
    private String priceOrder;
    private String commentOrder;
    private String timeOrder;
    private boolean orderAccepted;

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

    public void setPriceOrder(String priceOrder) {
        this.priceOrder = priceOrder;
    }

    public String getPriceOrder() {
        return priceOrder;
    }

    public void setCommentOrder(String commentOrder) {
        this.commentOrder = commentOrder;
    }

    public String getCommentOrder() {
        return commentOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        this.orderAccepted = orderAccepted;
    }

    public boolean isOrderAccepted() {
        return orderAccepted;
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

    @Override
    public String toString() {
        return "OrderModel{" +
                "idUserOrder='" + idUserOrder + '\'' +
                ", fromOrder='" + fromOrder + '\'' +
                ", destinationOrder='" + destinationOrder + '\'' +
                ", priceOrder=" + priceOrder +
                ", commentOrder='" + commentOrder + '\'' +
                ", timeOrder=" + timeOrder +
                ", orderAccepted=" + orderAccepted +
                '}';
    }
}
