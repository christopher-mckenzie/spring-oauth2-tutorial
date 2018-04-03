package com.oauth.tutorial

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@SpringBootApplication
@EnableOAuth2Sso
class OauthTutorialApplication extends WebSecurityConfigurerAdapter {

	@Override
	void configure(HttpSecurity http) throws Exception {
		http.antMatcher('/**')
				.authorizeRequests()
				.antMatchers('/', '/login**', '/webjars/**')
				.permitAll()
				.anyRequest()
				.authenticated()
	}

	static void main(String[] args) {
		SpringApplication.run OauthTutorialApplication, args
	}
}
