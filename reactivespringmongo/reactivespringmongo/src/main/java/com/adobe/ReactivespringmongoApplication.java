package com.adobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;



//@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactivespringmongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivespringmongoApplication.class, args);
	}

}
