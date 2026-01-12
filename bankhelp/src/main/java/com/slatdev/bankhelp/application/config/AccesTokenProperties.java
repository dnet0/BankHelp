package com.slatdev.bankhelp.application.config;

import java.security.Key;

public interface AccesTokenProperties {

	public Key getSecretKey();

	public String getSecret();

	public long getExpiration();
}
