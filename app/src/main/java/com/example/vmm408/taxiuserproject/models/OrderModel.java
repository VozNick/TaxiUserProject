package com.example.vmm408.taxiuserproject.models;

import java.util.Date;

public class OrderModel {
    private String idUserOrder;
    private String fromOrder;
    private String destinationOrder;
    private int priceOrder;
    private String commentOrder;
    private Date timeOrder;
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

    public void setPriceOrder(int priceOrder) {
        this.priceOrder = priceOrder;
    }

    public int getPriceOrder() {
        return priceOrder;
    }

    public void setCommentOrder(String commentOrder) {
        this.commentOrder = commentOrder;
    }

    public String getCommentOrder() {
        return commentOrder;
    }

    public void setTimeOrder(Date timeOrder) {
        this.timeOrder = timeOrder;
    }

    public Date getTimeOrder() {
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
}
