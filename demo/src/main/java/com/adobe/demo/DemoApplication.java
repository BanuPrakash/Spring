package com.adobe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.adobe.demo.service.BookService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
//		String[] names = ctx.getBeanDefinitionNames();
//		for(String name : names) {
//			System.out.println(name);
//		}
		
		BookService service = ctx.getBean("bookService", BookService.class);
		service.doTask();
	}

}
