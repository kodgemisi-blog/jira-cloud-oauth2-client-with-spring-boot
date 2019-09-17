package com.kodgemisi.blog.jiraoauth2client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>Spring Security's OAuth2 client implementation requires the user be fully authenticated (no anonymous authentication)
 * before making an access token request. See {@link org.springframework.security.oauth2.client.token.AccessTokenProviderChain#obtainAccessToken}</p>
 *
 * <p>That's why we provide a way to login before making a request to Jira's API.</p>
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.authorizeRequests().anyRequest().fullyAuthenticated()
			.and()
			.formLogin().and().logout();
		//@formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// {noop} docs: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#core-services-in-memory-service
		auth.inMemoryAuthentication().withUser("kodgemisi").password("{noop}kodgemisi").roles("USER");
	}

}
