package com.application.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenticationService {

	private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
	private static final String AUTH_TOKEN = "mySuperSecureToken";

	public static Authentication getAuthentication(HttpServletRequest request) {

		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		if (request.getRequestURI().toLowerCase().contains("swagger") ||
			request.getRequestURI().toLowerCase().contains("api-docs") ) return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);

		if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
			throw new BadCredentialsException("Invalid API Key");
		}

		return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}
