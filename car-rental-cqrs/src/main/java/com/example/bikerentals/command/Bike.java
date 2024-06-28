package com.example.bikerentals.command;
import com.example.bikerentals.core.*;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateRoot;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate(snapshotTriggerDefinition = "bikeSnapshotDefinition")
public class Bike {

    @AggregateIdentifier
    private String bikeId;

    private boolean isAvailable;

    public Bike() {
    }

    @CommandHandler
    public Bike(RegisterBikeCommand command) {
        apply(new BikeRegisteredEvent(command.getBikeId(), command.getBikeType(), command.getLocation()));
    }

    @CommandHandler
    public void handle(RentBikeCommand command) {
        if (!this.isAvailable) {
            throw new IllegalStateException("Bike is already rented");
        }
        apply(new BikeRentedEvent(command.getBikeId(), command.getRenter()));
    }

    @CommandHandler
    public void handle(ReturnBikeCommand command) {
        if (this.isAvailable) {
            throw new IllegalStateException("Bike was already returned");
        }
        apply(new BikeReturnedEvent(command.getBikeId(), command.getLocation()));
    }

    @EventSourcingHandler
    protected void handle(BikeRegisteredEvent event) {
        this.bikeId = event.getBikeId();
        this.isAvailable = true;
    }

    @EventSourcingHandler
    protected void handle(BikeReturnedEvent event) {
        this.isAvailable = true;
    }

    @EventSourcingHandler
    protected void handle(BikeRentedEvent event) {
        this.isAvailable = false;
    }
}
