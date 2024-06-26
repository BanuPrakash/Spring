package com.example.shopapp.cfg;

import com.example.shopapp.service.PostService;
import com.example.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private  final CacheManager cacheManager;

//   @Scheduled(fixedRate = 1000)
    // clean every 30 min
    @Scheduled(cron = "0 0/30 * * * *")
    public void clearCache() {
        System.out.println("Clear Cache...");
        cacheManager.getCacheNames().forEach(name -> {
            cacheManager.getCache(name).clear();
        });
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return  builder.build();
    }

    @Bean
    PostService jsonPostService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                    .builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(PostService.class);
    }

    @Bean
    UserService jsonUserService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(UserService.class);
    }

    @Bean(name="posts-pool")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("USER-POST");
        executor.initialize();
        return executor;
//        Executors.newFixedThreadPool(10);
    }
}
