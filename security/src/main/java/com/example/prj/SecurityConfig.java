package com.example.prj;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	DataSource ds;
	
	@Autowired
	void configureJdbcAuthenticationManager(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(ds);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public InMemoryUserDetailsManager users() {
//		return new InMemoryUserDetailsManager(User.withUsername("banu")
//				.password("{noop}password")
//				.authorities("ROLE_READ").build(),
//				User.withUsername("admin")
//						.password("{noop}password")
//						.authorities("ROLE_READ", "ROLE_ADMIN").build());
//	}
//	
	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests()
//				.antMatchers("/posts").hasAnyRole("READ", "ADMIN")
//				.antMatchers("/authors").hasRole("ADMIN")
//				.antMatchers("/").permitAll()
				.anyRequest().authenticated()
				.and().formLogin()
				.and().build();
	}
}
