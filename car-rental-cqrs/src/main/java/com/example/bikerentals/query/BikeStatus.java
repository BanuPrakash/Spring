package com.example.bikerentals.query;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.context.annotation.Profile;



@Entity
public class BikeStatus {

    @Id
    private String bikeId;
    private String bikeType;
    private String location;
    private String renter;

    public BikeStatus() {
    }

    public BikeStatus(String bikeId, String bikeType, String location) {
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.location = location;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public String getBikeType() {
        return bikeType;
    }
}
