package com.example.shopapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
