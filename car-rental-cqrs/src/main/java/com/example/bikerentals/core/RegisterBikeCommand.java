package com.example.bikerentals.core;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RegisterBikeCommand{
    @TargetAggregateIdentifier
    String bikeId;
    String bikeType;
    String location;

    public RegisterBikeCommand() {

    }

    public RegisterBikeCommand(String bikeId, String bikeType, String location) {
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.location = location;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
