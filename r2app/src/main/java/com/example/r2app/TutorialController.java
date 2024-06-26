package com.example.r2app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/api")
public class TutorialController {
    Sinks.Many sink;
    @Autowired
    TutorialService tutorialService;
    public TutorialController() {
        sink =  Sinks.many().multicast().onBackpressureBuffer();
    }
    @GetMapping("/tutorials")
//    @ResponseStatus(HttpStatus.OK)
    public Flux<ServerSentEvent<Tutorial>> getAllTutorials() {
           Flux<Tutorial> tutorialFlux =  tutorialService.findAll();
        tutorialFlux.doOnNext(tutorial -> {
            sink.tryEmitNext(tutorial);
        }).subscribe();
            return sink.asFlux().map(e -> ServerSentEvent.builder(e).build());
    }

    @GetMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Tutorial> getTutorialById(@PathVariable("id") int id) {
        return tutorialService.findById(id);
    }

    @PostMapping("/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        sink.tryEmitNext(tutorial);
        return tutorialService.save(new Tutorial(0, tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
    }



    @DeleteMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTutorial(@PathVariable("id") int id) {
        return tutorialService.deleteById(id);
    }

    @DeleteMapping("/tutorials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAllTutorials() {
        return tutorialService.deleteAll();
    }

    @GetMapping("/tutorials/published")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Tutorial> findByPublished() {
        return tutorialService.findByPublished(true);
    }
}