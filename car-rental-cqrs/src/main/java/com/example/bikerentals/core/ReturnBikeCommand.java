package com.example.bikerentals.core;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class   ReturnBikeCommand{
    @TargetAggregateIdentifier
    String bikeId;
    String location;

    public ReturnBikeCommand(){

    }

    public ReturnBikeCommand(String bikeId, String location) {
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
