package com.example.bikerentals.api;

import com.example.bikerentals.core.ReturnBikeCommand;
import com.example.bikerentals.core.RegisterBikeCommand;
import com.example.bikerentals.core.RentBikeCommand;
import com.example.bikerentals.history.BikeHistory;
import com.example.bikerentals.query.BikeStatus;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryBackpressure;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/bikes")
public class RentalController {
    @Autowired
    private  CommandGateway commandGateway;
    @Autowired
    private  QueryGateway queryGateway;


    @PutMapping
    public CompletableFuture<String> register(@RequestParam("location") String location,
                                              @RequestParam(value = "bikeType", defaultValue = "regular") String bikeType) {
        return register(UUID.randomUUID().toString(), bikeType, location);
    }

    @PutMapping("{bikeId}")
    public CompletableFuture<String> register(@PathVariable("bikeId") String bikeId,
                                              @RequestParam(value = "bikeType", defaultValue = "regular") String bikeType,
                                              @RequestParam("location") String location) {
        return commandGateway.send(new RegisterBikeCommand(bikeId, bikeType, location));
    }

    @PostMapping("/{bikeId}/rent")
    public CompletableFuture<String> rent(@PathVariable("bikeId") String bikeId,
                                          @RequestParam("renter") String renter) {
        return commandGateway.send(new RentBikeCommand(bikeId, renter))
                .thenApply(r -> "Bike rented to " + renter)
                .exceptionally(Throwable::getMessage);
    }

    @PostMapping("/{bikeId}/return")
    public CompletableFuture<String> returnBike(@PathVariable("bikeId") String bikeId,
                                                @RequestParam("location") String location) {
        return commandGateway.send(new ReturnBikeCommand(bikeId, location))
                .thenApply(r -> "Bike returned in " + location)
                .exceptionally(Throwable::getMessage);
    }

    @GetMapping()
    public CompletableFuture<List<BikeStatus>> findAll() {
        return queryGateway.query("findAll", null, ResponseTypes.multipleInstancesOf(BikeStatus.class));
    }

    @GetMapping("/{bikeId}")
    public CompletableFuture<BikeStatus> findOne(@PathVariable("bikeId") String bikeId) {
        return queryGateway.query("findOne", bikeId, BikeStatus.class);
    }

    @GetMapping("/{bikeId}/history")
    public CompletableFuture<List<BikeHistory>> findMovements(@PathVariable("bikeId") String bikeId) {
        return queryGateway.query("locationHistory", bikeId, ResponseTypes.multipleInstancesOf(BikeHistory.class));
    }

    @GetMapping(value = "/{bikeId}/watch", produces = "text/event-stream")
    public Flux<BikeHistory> watch(@PathVariable("bikeId") String bikeId) {
        SubscriptionQueryResult<List<BikeHistory>, BikeHistory> response = queryGateway.subscriptionQuery("locationHistory", bikeId,
                ResponseTypes.multipleInstancesOf(BikeHistory.class),
                ResponseTypes.instanceOf(BikeHistory.class),
                SubscriptionQueryBackpressure.defaultBackpressure()
        );
        return response.initialResult().flatMapMany(Flux::fromIterable).concatWith(response.updates());
    }

    @GetMapping(value = "/generate")
    public void generateData(@RequestParam("bikes") int bikeCount,
                             @RequestParam(value = "bikeType", defaultValue = "regular") String bikeType,
                             @RequestParam("rentals") int rentalCount) {
        for (int i = 0; i < bikeCount; i++) {
            String bikeId = UUID.randomUUID().toString();
            CompletableFuture<?> result = commandGateway.send(new RegisterBikeCommand(bikeId, bikeType, randomLocation()));
            for (int c = 0; c < rentalCount; c++) {
                result = result.thenCompose(r -> commandGateway.send(new RentBikeCommand(bikeId, randomRenter())))
                        .thenCompose(r -> commandGateway.send(new ReturnBikeCommand(bikeId, randomLocation())));
            }
        }
    }

    private String randomRenter() {
        List<String> renters = Arrays.asList("Allan", "Steven", "Josh", "Jakub", "Ben", "Marc", "Sara", "Kim", "Greg", "Maria");
        return renters.get(ThreadLocalRandom.current().nextInt(renters.size()));
    }

    private String randomLocation() {
        List<String> locations = Arrays.asList("Delhi", "Pune", "Bengaluru", "Mysore", "Hyderabad", "Chennai");
        return locations.get(ThreadLocalRandom.current().nextInt(locations.size()));
    }

}
