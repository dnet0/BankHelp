package com.slatdev.bankhelp.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.slatdev.bankhelp.application.config.RefreshTokenProperties;

@Component
@ConfigurationProperties(prefix = "security.refresh-token")
public class JwtRefreshTokenProperties implements RefreshTokenProperties {
	private int expirationDays;

	@Override
	public int getExpirationDays() {
		return expirationDays;
	}

	public void setExpirationDays(int expirationDays) {
		this.expirationDays = expirationDays;
	}
}
