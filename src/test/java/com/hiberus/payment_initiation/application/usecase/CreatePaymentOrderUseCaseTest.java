package com.hiberus.payment_initiation.application.usecase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CreatePaymentOrderUseCaseTest {

	@Test
	void shouldFailUntilImplemented() {
		var useCase = new CreatePaymentOrderUseCase();
		assertThrows(UnsupportedOperationException.class, useCase::execute);
	}
}

