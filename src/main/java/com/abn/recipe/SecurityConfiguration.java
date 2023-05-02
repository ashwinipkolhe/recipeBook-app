package com.abn.recipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//@Value("${spring.security.user.name}")
	private String userName;
	private String password;

	// Create 2 users for demo
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER").and().withUser("admin")
				.password("{noop}password").roles("USER", "ADMIN");

	}

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
				.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.GET, "/findAllRecipes/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/create").hasRole("ADMIN").antMatchers(HttpMethod.PUT, "/update/**")
				.hasRole("ADMIN").antMatchers(HttpMethod.PATCH, "/update/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/delete/**").hasRole("ADMIN").and().csrf().disable().formLogin()
				.disable().headers().frameOptions().disable();
	} 
}