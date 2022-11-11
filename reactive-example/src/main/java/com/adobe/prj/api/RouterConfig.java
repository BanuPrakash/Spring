package com.adobe.prj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.adobe.prj.service.CustomerService;

//https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework
@Configuration
public class RouterConfig {
	@Autowired
	CustomerService service;
	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
				.GET("/route/customers", service::handleLoadCustomers)
				.build();
	}
}
