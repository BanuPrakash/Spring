package com.adobe.document;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;

import com.adobe.repo.ItemReactiveCappedRepository;

import reactor.core.publisher.Flux;

@Component
public class ItemDataInitializer implements CommandLineRunner {

    @Autowired
    ItemReactiveCappedRepository itemReactiveCappedRepository;

    @Autowired
    ReactiveMongoOperations mongoOperations;

    @Override
    public void run(String... args) throws Exception {

        createCappedCollection();
        dataSetUpforCappedCollection();
    }

    private void createCappedCollection() {
        mongoOperations.dropCollection(ItemCapped.class)
        .then(mongoOperations.createCollection(ItemCapped.class, CollectionOptions.empty().capped().maxDocuments(20).size(50000)));
        
    }

    

    public void dataSetUpforCappedCollection(){

        Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofSeconds(1))
                .map(i -> new ItemCapped(null,"Random Item " + i, (100.00+i)));

        itemReactiveCappedRepository
                .insert(itemCappedFlux)
                .subscribe((itemCapped -> {
                    System.out.println("Inserted Item is " + itemCapped);
                }));

    }

    



}