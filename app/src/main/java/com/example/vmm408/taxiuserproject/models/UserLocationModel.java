package com.example.vmm408.taxiuserproject.models;

public class UserLocationModel {
    private int idUserLocation;
    private double latitudeUserLocation;
    private double longitudeUserLocation;

    public void setIdUserLocation(int idUserLocation) {
        this.idUserLocation = idUserLocation;
    }

    public int getIdUserLocation() {
        return idUserLocation;
    }

    public void setLatitudeUserLocation(double latitudeUserLocation) {
        this.latitudeUserLocation = latitudeUserLocation;
    }

    public double getLatitudeUserLocation() {
        return latitudeUserLocation;
    }

    public void setLongitudeUserLocation(double longitudeUserLocation) {
        this.longitudeUserLocation = longitudeUserLocation;
    }

    public double getLongitudeUserLocation() {
        return longitudeUserLocation;
    }
}
