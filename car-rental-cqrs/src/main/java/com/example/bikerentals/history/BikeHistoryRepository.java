package com.example.bikerentals.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeHistoryRepository extends JpaRepository<BikeHistory, Long> {

    List<BikeHistory> findByBikeIdOrderById(String bikeId);

}