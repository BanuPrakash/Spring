package com.xiaomi.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class OrderappApplication {
	
	@Autowired
	CacheManager cacheManager;	
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

	//@Scheduled(fixedDelay = 2000)
	@Scheduled(cron ="0 0/30 * * * *")
	public void clearCache() {
		System.out.println("Cache Cleared!!!");
		cacheManager.getCacheNames().forEach(cache -> {
			cacheManager.getCache(cache).clear();
		});
	}
	
	@Bean
	 public OpenAPI customOpenAPI() {
	     return new OpenAPI()
	          .info(new Info()
	          .title("Order application API")
	          .version("1.0")
	          .description("End points for Orderapplication")
	          .termsOfService("http://swagger.io/terms/")
	          .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	    }
	
}
