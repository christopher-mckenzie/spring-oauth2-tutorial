package com.oauth.tutorial.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

/**
 * Created by mckenzie on 4/3/18.
 */
@Configuration
@EnableResourceServer
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    @Override
    void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.antMatcher('/me').authorizeRequests().anyRequest().authenticated()
    }
}
