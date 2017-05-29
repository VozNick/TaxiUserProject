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
    private DriverLocationModel driverLocationModel;
}
