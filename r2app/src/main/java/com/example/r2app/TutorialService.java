package com.example.r2app;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public Flux<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }

    public Flux<Tutorial> findByTitleContaining(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    public Mono<Tutorial> findById(int id) {
        return tutorialRepository.findById(id);
    }

    public Mono<Tutorial> save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }


    public Mono<Void> deleteById(int id) {
        return tutorialRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return tutorialRepository.deleteAll();
    }

    public Flux<Tutorial> findByPublished(boolean isPublished) {
        return tutorialRepository.findByPublished(isPublished);
    }
}