package com.kodgemisi.blog.jiraoauth2client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
@EnableOAuth2Client
public class AtlassianOAuth2ClientApp {

	public static void main(String[] args) {
		SpringApplication.run(AtlassianOAuth2ClientApp.class, args);
	}

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	OAuth2ProtectedResourceDetails oauth2RemoteResource() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, @Value("${app.jiraUrl}") String baseUrl) {
		final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oauth2RemoteResource(), oauth2ClientContext);
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
		return restTemplate;
	}

	@Bean
	RedirectStrategy oAuth2RedirectStrategy() {
		return new OAuth2RedirectStrategy();
	}

	/**
	 * It is always preferable to reuse the defaults and not overriding them. Hence, in this method instead of creating a new bean
	 * we are configuring default {@link org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter} bean and reusing it.
	 */
	@Bean
	OAuth2ClientContextFilter oAuth2ClientContextFilter(OAuth2ClientContextFilter oAuth2ClientContextFilter) {
		oAuth2ClientContextFilter.setRedirectStrategy(oAuth2RedirectStrategy());
		return oAuth2ClientContextFilter;
	}
}
