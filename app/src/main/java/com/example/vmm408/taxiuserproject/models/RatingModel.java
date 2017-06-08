package com.example.vmm408.taxiuserproject.models;

public class RatingModel {
    private int idUserRating;
    private String commentsRating;
    private int starsRating;

    public void setIdUserRating(int idUserRating) {
        this.idUserRating = idUserRating;
    }

    public int getIdUserRating() {
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
}
