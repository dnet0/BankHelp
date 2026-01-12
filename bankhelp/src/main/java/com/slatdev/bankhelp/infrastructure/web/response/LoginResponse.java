package com.slatdev.bankhelp.infrastructure.web.response;

import java.util.UUID;

public record LoginResponse(String accessToken, UUID refreshToken) {

}
