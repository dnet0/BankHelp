package com.slatdev.bankhelp.infrastructure.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.domain.model.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtilAuthToken implements AuthTokenService {

	private final JwtAccesTokenProperties properties;

	public JwtUtilAuthToken(JwtAccesTokenProperties properties) {
		this.properties = properties;
	}

	@Override
	public String generateToken(User user) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + this.properties.getExpiration());
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("userId", user.getId().toString());
		return Jwts.builder().setClaims(claims).setSubject(user.getEmail()).setIssuedAt(now)
				.setExpiration(expirationDate).signWith(this.properties.getSecretKey()).compact();
	}

	@Override
	public String extractUserName(String token) {
		return Jwts.parserBuilder().setSigningKey(this.properties.getSecretKey()).build().parseClaimsJws(token)
				.getBody().getSubject();
	}

	@Override
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.properties.getSecretKey()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

}
