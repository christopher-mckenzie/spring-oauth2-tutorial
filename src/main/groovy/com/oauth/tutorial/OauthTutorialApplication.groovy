package com.oauth.tutorial

import com.oauth.tutorial.config.ClientResources
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.filter.CompositeFilter

import javax.servlet.Filter

@SpringBootApplication
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class OauthTutorialApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oAuth2ClientContext

	Filter ssoFilter(){
		return new CompositeFilter(filters: [ssoFilter(facebook(), '/login/facebook'), ssoFilter(github(), '/login/github')])
	}


	Filter ssoFilter(ClientResources clientResources, String path){
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path)
		OAuth2RestTemplate template = new OAuth2RestTemplate(clientResources.client, oAuth2ClientContext)
		filter.restTemplate = template
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(clientResources.resource.userInfoUri, clientResources.client.clientId)
		tokenServices.restTemplate = template
		filter.tokenServices = tokenServices
		return filter
	}
	@Bean
	@ConfigurationProperties('facebook')
	ClientResources facebook(){
		return new ClientResources()
	}

	@Bean
	@ConfigurationProperties('github')
	ClientResources github(){
		return new ClientResources()
	}

	@Bean
	FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter){
		return new FilterRegistrationBean(filter: filter, order: -100)
	}

	@Override
	void configure(HttpSecurity http) throws Exception {
		http.antMatcher('/**')
				.authorizeRequests()
				.antMatchers('/', '/login**', '/webjars/**')
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint('/'))
				.and()
				.logout()
				.logoutSuccessUrl('/')
				.permitAll()
				.and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter)
	}

	static void main(String[] args) {
		SpringApplication.run OauthTutorialApplication, args
	}
}
