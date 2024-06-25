package com.example.reactivebasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieDao movieDao;

    @PostMapping()
    public Mono<String> addMovie(@RequestBody Movie movie) {
        movieDao.save(movie).subscribe();
        return Mono.just("Movie added!!!");
    }
    // SSE
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> getMovies() {
        return movieDao.findBy();
    }
}
