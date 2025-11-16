package com.example.dockergithubactions.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtils {

	private final Key key;

	public JwtUtils() {
		// Tworzymy bezpieczny 256-bitowy klucz dla HMAC-SHA
		key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}

	public String generateJwtToken(final String username) {
		return Jwts.builder()
				.setSubject(username)
				.signWith(key)
				.compact();
	}

	public boolean validateJwtToken(final String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	public String getUsernameFromJwtToken(final String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}