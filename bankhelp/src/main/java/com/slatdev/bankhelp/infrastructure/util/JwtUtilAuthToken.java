package com.slatdev.bankhelp.infrastructure.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.domain.model.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilAuthToken implements AuthTokenService {

	private static final Key KEY_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // Duracion dos horas(en ms)

	@Override
	public String generateToken(User user) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(KEY_SECRET).compact();
	}

	@Override
	public String extractUserName(String token) {
		return Jwts.parserBuilder().setSigningKey(KEY_SECRET).build().parseClaimsJws(token).getBody().getSubject();
	}

	@Override
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(KEY_SECRET).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

}
