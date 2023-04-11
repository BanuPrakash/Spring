package com.example.prj;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	@GetMapping("/")
	String home() {
		return "Hello Spring Security!!!";
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('READ')")
	@GetMapping("/posts")
	String getPosts() {
		return "Posts!!!";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/authors")
	String getAuthors() {
		return "Authors!!!";
	}
}
