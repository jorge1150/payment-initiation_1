package com.hiberus.payment_initiation.application.usecase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GetPaymentOrderUseCaseTest {

	@Test
	void shouldReturnEmptyUntilRepositoryIsInjected() {
		var useCase = new GetPaymentOrderUseCase();
		assertTrue(useCase.execute("PO-123").isEmpty());
	}
}

