package com.xiaomi.prj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.xiaomi.prj.service.AppService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
		String[] beans = ctx.getBeanDefinitionNames();
		for(String bean : beans) {
			System.out.println(bean);
		}
		
		AppService service = ctx.getBean("appService", AppService.class);
		service.insert();
	}

}
