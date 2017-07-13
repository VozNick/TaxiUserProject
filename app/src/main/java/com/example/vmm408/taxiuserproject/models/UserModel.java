package com.example.vmm408.taxiuserproject.models;

public class UserModel {
    private String idUser;
    private String avatarUser;
    private String fullNameUser;
    private String genderUser;
    private String ageUser;
    private String phoneUser;
    private double ratingUser;
    private String idCurrentOrder;

    private double experienceDriver;
    private String carModelDriver;
    private String numPlateCarDriver;

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setAvatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
    }

    public String getAvatarUser() {
        return avatarUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setGenderUser(String genderUser) {
        this.genderUser = genderUser;
    }

    public String getGenderUser() {
        return genderUser;
    }

    public void setAgeUser(String ageUser) {
        this.ageUser = ageUser;
    }

    public String getAgeUser() {
        return ageUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setRatingUser(double ratingUser) {
        this.ratingUser = ratingUser;
    }

    public double getRatingUser() {
        return ratingUser;
    }

    public void setIdCurrentOrder(String idCurrentOrder) {
        this.idCurrentOrder = idCurrentOrder;
    }

    public String getIdCurrentOrder() {
        return idCurrentOrder;
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

    public static class User {
        private static UserModel userModel = new UserModel();

        public static void setUserModel(UserModel userModel) {
            User.userModel = userModel;
        }

        public static UserModel getUserModel() {
            return userModel;
        }
    }
}
