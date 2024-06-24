package com.example.shopapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableCaching

public class ShopappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopappApplication.class, args);
    }


//    @Bean
//    public DateTimeFormatterRegistrar dateTimeFormatterRegistrar() {
//        return new DateTimeFormatterRegistrar() {
//            @Override
//            public void registerFormatters(FormattingConversionService conversionService) {
//                conversionService.addFormatterForFieldType(Date.class, new DateTimeFormatter("yyyy-MM-dd"));
//            }
//        };
//    }
}
