package com.example.reactivebasic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveBasicApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            // Netflix Streamer
            // each scene plays for 2 seconds
            Flux<String> netFlix = Flux.fromStream(() -> getMovie())
                    .delayElements(Duration.ofSeconds(2));

            // person 1
            netFlix.subscribe(scence -> System.out.println("Person 1 is watching " + scence));

            // person 2 joins after few seconds
            try {
                Thread.sleep(3000);
            }catch (InterruptedException ex) { ex.printStackTrace();}
            netFlix.subscribe(scence -> System.out.println("Person 2 is watching " + scence));

        };
    }

    Stream<String> getMovie() {
        System.out.println("Got the movie request!!!");
        return Stream.of(
          "scene 1",
                "scene 2",
                "scene 3",
                "scene 4",
                "scene 5"
        );
    }
}
