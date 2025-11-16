package com.example.dockergithubactions.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;

	public JwtAuthFilter(final JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request,
									final HttpServletResponse response,
									final FilterChain filterChain)
			throws ServletException, IOException {

		final String header = request.getHeader("Authorization");

		// ⛔ Jeśli brak nagłówka – przepuść dalej i nic nie rób
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = header.substring(7);

		try {
			if (jwtUtils.validateJwtToken(token)) {
				final String username = jwtUtils.getUsernameFromJwtToken(token);

				final UsernamePasswordAuthenticationToken auth =
						new UsernamePasswordAuthenticationToken(
								username,
								null,
								Collections.emptyList()
						);

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (final Exception e) {
			// ⛔ ZŁAP wyjątek — w przeciwnym razie Security wywali 403
			// i NIE TWÓRZ własnej odpowiedzi
		}

		filterChain.doFilter(request, response);
	}
}
