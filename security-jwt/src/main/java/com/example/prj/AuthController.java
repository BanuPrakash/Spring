package com.example.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	@Autowired
	TokenService service;
	
	@PostMapping("/token")
	public String token(Authentication authentication) {
		String token = service.generateToken(authentication);
		return token;
	}
}
