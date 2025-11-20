package com.hiberus.payment_initiation.infrastructure.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentOrderRestControllerTest {

	@Test
	void shouldInstantiateController() {
		assertNotNull(new PaymentOrderRestController());
	}
}

