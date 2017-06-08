package com.example.vmm408.taxiuserproject.models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private int idUser;
    private int avatarUser;
    private String nameUser;
    private String lastNameUser;
    private String sexUser;
    private int ageUser;
    private String phoneUser;
    private String emailUser;
    private String passwordUser;
    private double experienceDriver;
    private String carModelDriver;
    private String numPlateCarDriver;

    private List<OrderModel> orderModelList = new ArrayList<>();
    private List<RatingModel> ratingModelList = new ArrayList<>();

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setAvatarUser(int avatarUser) {
        this.avatarUser = avatarUser;
    }

    public int getAvatarUser() {
        return avatarUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setSexUser(String sexUser) {
        this.sexUser = sexUser;
    }

    public String isSexUser() {
        return sexUser;
    }

    public void setAgeUser(int ageUser) {
        this.ageUser = ageUser;
    }

    public int getAgeUser() {
        return ageUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setExperienceDriver(double experienceDriver) {
        this.experienceDriver = experienceDriver;
    }

    public double getExperienceDriver() {
        return experienceDriver;
    }

    public void setCarModelDriver(String carModelDriver) {
        this.carModelDriver = carModelDriver;
    }

    public String getCarModelDriver() {
        return carModelDriver;
    }

    public void setNumPlateCarDriver(String numPlateCarDriver) {
        this.numPlateCarDriver = numPlateCarDriver;
    }

    public String getNumPlateCarDriver() {
        return numPlateCarDriver;
    }

    public void setOrderModelList(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public List<OrderModel> getOrderModelList() {
        return orderModelList;
    }

    public void setRatingModelList(List<RatingModel> ratingModelList) {
        this.ratingModelList = ratingModelList;
    }

    public List<RatingModel> getRatingModelList() {
        return ratingModelList;
    }

    public static class User {
        private static UserModel userModel = new UserModel();

        public User() {
        }

        public static void setUserModel(UserModel userModel) {
            User.userModel = userModel;
        }

        public static UserModel getUserModel() {
            return userModel;
        }
    }
}
