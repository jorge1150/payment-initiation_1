package com.hiberus.payment_initiation.infrastructure.rest;

import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentOrdersControllerTest {

	@Mock
	private CreatePaymentOrderUseCase createPaymentOrderUseCase;

	@Mock
	private GetPaymentOrderUseCase getPaymentOrderUseCase;

	private PaymentOrdersController paymentOrdersController;

	@BeforeEach
	void setUp() {
		paymentOrdersController = new PaymentOrdersController(createPaymentOrderUseCase, getPaymentOrderUseCase);
	}

	@Test
	void shouldInstantiateController() {
		assertThat(paymentOrdersController).isNotNull();
	}

	@Test
	void shouldReturnNotImplementedForInitiatePaymentOrder() {
		// Given
		PaymentOrderInitiationRequest request = new PaymentOrderInitiationRequest();

		// When
		ResponseEntity<com.hiberus.payment_initiation.generated.model.PaymentOrderResource> response =
				paymentOrdersController.initiatePaymentOrder(request);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
	}

	@Test
	void shouldReturnNotImplementedForGetPaymentOrderById() {
		// Given
		String id = "PO-123";

		// When
		ResponseEntity<com.hiberus.payment_initiation.generated.model.PaymentOrderResource> response =
				paymentOrdersController.getPaymentOrderById(id);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
	}

	@Test
	void shouldReturnNotImplementedForGetPaymentOrderStatusById() {
		// Given
		String id = "PO-123";

		// When
		ResponseEntity<com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource> response =
				paymentOrdersController.getPaymentOrderStatusById(id);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
	}
}

