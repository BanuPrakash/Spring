package com.example.bikerentals.core;




public class BikeRentedEvent{
    String bikeId;
    String  renter;

    public BikeRentedEvent() {
    }

    public BikeRentedEvent(String bikeId, String renter) {
        this.bikeId = bikeId;
        this.renter = renter;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }
}