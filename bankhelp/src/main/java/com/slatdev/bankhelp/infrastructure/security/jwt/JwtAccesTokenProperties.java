package com.slatdev.bankhelp.infrastructure.security.jwt;

import java.security.Key;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.slatdev.bankhelp.application.config.AccesTokenProperties;

import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtAccesTokenProperties implements AccesTokenProperties{
	private String secret;
	private long expiration;

	@Override
	public Key getSecretKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	@Override
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

}
