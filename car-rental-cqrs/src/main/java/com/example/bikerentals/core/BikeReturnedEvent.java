package com.example.bikerentals.core;

public class BikeReturnedEvent{
    String bikeId;
    String location;

    public BikeReturnedEvent() {
    }

    public BikeReturnedEvent(String bikeId, String location) {
        this.bikeId = bikeId;
        this.location = location;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}