package com.example.demo;

import com.example.demo.model.Employee;
import com.example.demo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx =
                SpringApplication.run(DemoApplication.class, args);

        String names[] = ctx.getBeanDefinitionNames();
        for(String name: names) {
            System.out.println(name);
        }

        AppService service = ctx.getBean("appService", AppService.class);
        service.insert(Employee.builder().email("a@gmail.com").firstName("A").build());
    }

}
