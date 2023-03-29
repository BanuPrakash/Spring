package com.xiaomi.prj.cfg;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

	@Autowired
	 private  ObjectMapper objectMapper;
	 
	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(60))
				.disableCachingNullValues()
				//.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
//		                new StringRedisSerializer()))
		            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
		                new GenericJackson2JsonRedisSerializer(objectMapper)));
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("productCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
				.withCacheConfiguration("customerCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
	}
}