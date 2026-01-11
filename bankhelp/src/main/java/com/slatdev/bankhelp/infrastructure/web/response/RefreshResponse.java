package com.slatdev.bankhelp.infrastructure.web.response;

import java.util.UUID;

public record RefreshResponse(String accesstoken, UUID refreshToken) {

}