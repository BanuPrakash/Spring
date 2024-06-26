package com.example.springsecuritydemo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.DelegatingFilterProxy;

@RestController
public class SampleController {



    @GetMapping("/")
    public String greet() {
        return "Hello World!!!";
    }

    @GetMapping("/user")
    public String greetUser() {
        return "Hello User!!!";
    }
    @GetMapping("/admin")
    public String greetAdim() {
        return "Hello Admin!!!";
    }
}
