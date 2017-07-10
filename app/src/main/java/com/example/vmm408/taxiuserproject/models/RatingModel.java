package com.example.vmm408.taxiuserproject.models;

public class RatingModel {
    private String idUserRating;
    private String commentsRating;
    private int starsRating;

    private UserModel userModel;

    public void setIdUserRating(String idUserRating) {
        this.idUserRating = idUserRating;
    }

    public String getIdUserRating() {
        return idUserRating;
    }

    public void setCommentsRating(String commentsRating) {
        this.commentsRating = commentsRating;
    }

    public String getCommentsRating() {
        return commentsRating;
    }

    public void setStarsRating(int starsRating) {
        this.starsRating = starsRating;
    }

    public int getStarsRating() {
        return starsRating;
    }

    @Override
    public String toString() {
        return "RatingModel{" +
                "idUserRating='" + idUserRating + '\'' +
                ", commentsRating='" + commentsRating + '\'' +
                ", starsRating=" + starsRating +
                ", userModel=" + userModel +
                '}';
    }
}
