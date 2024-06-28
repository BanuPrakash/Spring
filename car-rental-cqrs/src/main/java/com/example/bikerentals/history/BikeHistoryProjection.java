package com.example.bikerentals.history;
import com.example.bikerentals.core.BikeRegisteredEvent;
import com.example.bikerentals.core.BikeRentedEvent;
import com.example.bikerentals.core.BikeReturnedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;


@Component
public class BikeHistoryProjection {

    private final BikeHistoryRepository bikeHistoryRepository;
    private final QueryUpdateEmitter updateEmitter;

    public BikeHistoryProjection(BikeHistoryRepository bikeHistoryRepository, QueryUpdateEmitter updateEmitter) {
        this.bikeHistoryRepository = bikeHistoryRepository;
        this.updateEmitter = updateEmitter;
    }

    @EventHandler
    public void handle(BikeRegisteredEvent event, @Timestamp Instant timestamp) {
        BikeHistory register = new BikeHistory();
        register.setBikeId(event.getBikeId());
        register.setDescription("Bike (" + event.getBikeType() + ") registered in ");
        register.setTimestamp(timestamp.toString());

        bikeHistoryRepository.save(register);
    }

    @EventHandler
    public void handle(BikeRentedEvent event, @Timestamp Instant timestamp) {
        BikeHistory newEntry = new BikeHistory();
        newEntry.setBikeId(event.getBikeId());
        newEntry.setTimestamp(timestamp.toString());
        newEntry.setDescription("Bike rented out to " + event.getRenter());
        bikeHistoryRepository.save(newEntry);

        updateEmitter.emit(m -> "locationHistory".equals(m.getQueryName())
                        && newEntry.getBikeId().equals(m.getPayload()),
                newEntry);
    }

    @EventHandler
    public void handle(BikeReturnedEvent event, @Timestamp Instant timestamp) {
        BikeHistory newEntry =  new BikeHistory();
        newEntry.setBikeId(event.getBikeId());
                newEntry.setTimestamp(timestamp.toString());
                newEntry.setDescription( "Bike returned in " + event.getLocation());

        bikeHistoryRepository.save(newEntry);

        updateEmitter.emit(m -> "locationHistory".equals(m.getQueryName())
                        && newEntry.getBikeId().equals(m.getPayload()),
                newEntry);
    }

    @QueryHandler(queryName = "locationHistory")
    public List<BikeHistory> findMovements(String bikeId) {
        return bikeHistoryRepository.findByBikeIdOrderById(bikeId);
    }

}
