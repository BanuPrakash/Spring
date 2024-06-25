package com.example.reactivebasic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static java.time.Duration.ofSeconds;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveBasicApplication {
    @Autowired
    private MovieDao movieDao;

    @Value("classpath:movies.json")
    private Resource resource;

    public static void main(String[] args) {
        SpringApplication.run(ReactiveBasicApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            List<Movie> movieList = mapper.readValue(resource.getInputStream(),
                    new TypeReference<List<Movie>>() {
                    });

            Flux.fromIterable(movieList)
                    .delayElements(Duration.ofSeconds(2))
                    .flatMap(movieDao::save)
                    .doOnComplete(() -> System.out.println("complete!!!"))
                    .subscribe();
        };

    }
//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            // Netflix Streamer
//            // each scene plays for 2 seconds
//            Flux<String> netFlix = Flux.fromStream(() -> getMovie())
//                    .delayElements(Duration.ofSeconds(2));
//
//            ConnectableFlux connectableFlux = netFlix.publish();
//
//            connectableFlux.connect();
//            // person 1
//            connectableFlux.subscribe(scence -> System.out.println("Person 1 is watching " + scence));
//
//            // person 2 joins after few seconds
//            try {
//                Thread.sleep(3000);
//            }catch (InterruptedException ex) { ex.printStackTrace();}
//            connectableFlux.subscribe(scence -> System.out.println("Person 2 is watching " + scence));
//
//        };
//    }

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
