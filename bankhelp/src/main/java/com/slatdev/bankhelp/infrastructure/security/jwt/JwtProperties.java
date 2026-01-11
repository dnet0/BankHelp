package com.slatdev.bankhelp.infrastructure.security.jwt;

import java.security.Key;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
	private String secret;
	private long expiration;

	public Key getSecretKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

}
