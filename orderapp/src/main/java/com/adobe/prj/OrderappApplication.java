package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableHypermediaSupport(type=HypermediaType.HAL_FORMS)
public class OrderappApplication {

	@Autowired
	CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public OpenAPI springOpenApi() {
			return new OpenAPI().
					info(new Info().title("Order Application")
							.description("Spring Boot application"));
	}
	//https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
//	@Scheduled(cron = "0 0/30 * * * *")
//	@Scheduled(fixedRate = 3000)
	public void clearCache() {
		System.out.println("Cache Cleared!!!");
		cacheManager.getCacheNames().forEach(cache -> {
			cacheManager.getCache(cache).clear();
		});
	}
	
}
