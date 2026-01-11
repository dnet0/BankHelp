package com.slatdev.bankhelp.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.refresh-token")
public class RefreshTokenProperties {
	private int expirationDays;

	public int getExpirationDays() {
		return expirationDays;
	}

	public void setExpirationDays(int expirationDays) {
		this.expirationDays = expirationDays;
	}

}
