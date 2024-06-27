package com.example.oauth2github;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SampleController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello from Secure endpoint!!!";
    }
}
