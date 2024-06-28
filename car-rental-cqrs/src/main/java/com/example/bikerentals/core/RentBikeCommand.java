package com.example.bikerentals.core;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class RentBikeCommand{
    @TargetAggregateIdentifier
    String bikeId;
    String renter;

    public RentBikeCommand() {
    }

    public RentBikeCommand(String bikeId, String renter) {
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
