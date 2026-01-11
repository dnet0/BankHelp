package com.slatdev.bankhelp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.slatdev.bankhelp.infrastructure.web.AuthController;
import com.slatdev.bankhelp.infrastructure.web.TicketController;

@SpringBootTest
class BankhelpApplicationTests {

	@Autowired
	private AuthController authController;
	@Autowired
	private TicketController ticketController;
	@Test
	void contextLoads() {
		assertThat(authController).isNotNull();
		assertThat(ticketController).isNotNull();
	}

}
