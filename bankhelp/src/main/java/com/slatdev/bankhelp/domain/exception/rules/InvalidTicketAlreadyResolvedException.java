package com.slatdev.bankhelp.domain.exception.rules;

import com.slatdev.bankhelp.domain.exception.DomainRuleException;

public class InvalidTicketAlreadyResolvedException extends DomainRuleException {

	private static final long serialVersionUID = 1L;

	public InvalidTicketAlreadyResolvedException(String message) {
		super(message);
	}
}