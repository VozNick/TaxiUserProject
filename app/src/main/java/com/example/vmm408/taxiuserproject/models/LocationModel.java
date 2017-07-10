package com.example.vmm408.taxiuserproject.models;

public class LocationModel {
    private String idUserLocation;
    private double latitudeLocation;
    private double longitudeLocation;

    public void setIdUserLocation(String idUserLocation) {
        this.idUserLocation = idUserLocation;
    }

    public String getIdUserLocation() {
        return idUserLocation;
    }

    public void setLatitudeLocation(double latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public double getLatitudeLocation() {
        return latitudeLocation;
    }

    public void setLongitudeLocation(double longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }

    public double getLongitudeLocation() {
        return longitudeLocation;
    }
}
