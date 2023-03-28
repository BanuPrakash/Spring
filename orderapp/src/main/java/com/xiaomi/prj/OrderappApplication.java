package com.xiaomi.prj;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
}
