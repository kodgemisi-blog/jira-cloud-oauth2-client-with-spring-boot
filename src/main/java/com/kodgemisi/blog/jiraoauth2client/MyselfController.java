package com.kodgemisi.blog.jiraoauth2client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/myself")
class MyselfController {

	private final OAuth2RestTemplate restTemplate;

	@GetMapping
	ResponseEntity<Map<?, ?>> myself(WebRequest request) {

		final Map<?, ?> result = restTemplate.getForObject("/myself", Map.class);

		return ResponseEntity.ok(result);
	}

}
