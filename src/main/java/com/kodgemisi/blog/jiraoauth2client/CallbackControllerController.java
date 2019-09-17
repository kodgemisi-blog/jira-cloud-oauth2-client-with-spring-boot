package com.kodgemisi.blog.jiraoauth2client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Created on September, 2019
 *
 * @author destan
 */
@Slf4j
@Controller
@RequiredArgsConstructor
class CallbackControllerController {

	private final OAuth2RestTemplate restTemplate;

	@GetMapping("/callback")
	String callback(HttpSession httpSession) {

		final SavedRequest savedRequest = (SavedRequest) httpSession.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		httpSession.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");

		// This gets an access token using the code and state values which are in the query parameters and
		//stores the access token in OAuth2ClientContext automatically. Hence, we can redirect to previous URL without
		//code & state params.
		restTemplate.getAccessToken();

		// Recall that savedRequest.redirectUrl is set from currentUri which is stripped off of its query parameters
		//in OAuth2ClientContextFilter#calculateCurrentUri
		return "redirect:" + savedRequest.getRedirectUrl();
	}

}
