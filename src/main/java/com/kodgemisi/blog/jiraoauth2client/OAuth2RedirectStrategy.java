package com.kodgemisi.blog.jiraoauth2client;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created on September, 2019
 *
 * @author destan
 */
class OAuth2RedirectStrategy extends DefaultRedirectStrategy {

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

		final String currentUri = (String) request.getAttribute("currentUri");
		final HttpSession session = request.getSession(false);

		if (session != null) {
			// SPRING_SECURITY_SAVED_REQUEST = org.springframework.security.web.savedrequest.HttpSessionRequestCache#SAVED_REQUEST
			// Making it SimpleSavedRequest instead of DefaultSavedRequest makes HttpSessionRequestCache ignore it (matchesSavedRequest returns false)
			//so it lands on our callback smoothly.
			session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", new SimpleSavedRequest(currentUri));
		}

		super.sendRedirect(request, response, url);
	}

}
