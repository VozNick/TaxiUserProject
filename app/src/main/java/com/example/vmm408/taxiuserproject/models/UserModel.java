package com.example.vmm408.taxiuserproject.models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private int idUser;
    private int avatarUser;
    private String nameUser;
    private String lastNameUser;
    private boolean sexUser;
    private int ageUser;
    private int phoneUser;
    private String emailUser;
    private String passwordUser;
    private String commentsUser;
    private int ratingsUser;

    private List<OrderUserModel> orderUserModelList = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(int idUser,
                     int avatarUser,
                     String nameUser,
                     String lastNameUser,
                     boolean sexUser,
                     int ageUser,
                     int phoneUser,
                     String emailUser,
                     String passwordUser,
                     String commentsUser,
                     int ratingsUser) {
        this.idUser = idUser;
        this.avatarUser = avatarUser;
        this.nameUser = nameUser;
        this.lastNameUser = lastNameUser;
        this.sexUser = sexUser;
        this.ageUser = ageUser;
        this.phoneUser = phoneUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.commentsUser = commentsUser;
        this.ratingsUser = ratingsUser;
    }

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

    public void setSexUser(boolean sexUser) {
        this.sexUser = sexUser;
    }

    public boolean isSexUser() {
        return sexUser;
    }

    public void setAgeUser(int ageUser) {
        this.ageUser = ageUser;
    }

    public int getAgeUser() {
        return ageUser;
    }

    public void setPhoneUser(int phoneUser) {
        this.phoneUser = phoneUser;
    }

    public int getPhoneUser() {
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

    public void setCommentsUser(String commentsUser) {
        this.commentsUser = commentsUser;
    }

    public String getCommentsUser() {
        return commentsUser;
    }

    public void setRatingsUser(int ratingsUser) {
        this.ratingsUser = ratingsUser;
    }

    public int getRatingsUser() {
        return ratingsUser;
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
