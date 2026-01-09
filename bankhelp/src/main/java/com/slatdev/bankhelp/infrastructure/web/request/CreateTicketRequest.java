package com.slatdev.bankhelp.infrastructure.web.request;

import java.util.UUID;

public record CreateTicketRequest(UUID userId, String description) {

}
