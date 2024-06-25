package com.example.reactivebasic;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MovieDao extends ReactiveMongoRepository<Movie, String> {
    @Tailable
    Flux<Movie> findBy();
}
