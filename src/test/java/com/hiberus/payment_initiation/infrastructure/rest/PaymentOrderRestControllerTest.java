package com.hiberus.payment_initiation.infrastructure.rest;

import com.hiberus.payment_initiation.application.dto.CreatePaymentOrderCommand;
import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import com.hiberus.payment_initiation.generated.model.PaymentOrderResource;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource;
import com.hiberus.payment_initiation.infrastructure.rest.mapper.PaymentOrderRestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentOrdersControllerTest {

	@Mock
	private CreatePaymentOrderUseCase createPaymentOrderUseCase;

	@Mock
	private GetPaymentOrderUseCase getPaymentOrderUseCase;

	@Mock
	private PaymentOrderRestMapper paymentOrderRestMapper;

	private PaymentOrdersController paymentOrdersController;

	@BeforeEach
	void setUp() {
		paymentOrdersController = new PaymentOrdersController(
				createPaymentOrderUseCase,
				getPaymentOrderUseCase,
				paymentOrderRestMapper
		);
	}

	@Test
	void shouldInstantiateController() {
		assertThat(paymentOrdersController).isNotNull();
	}

	@Test
	void shouldReturnCreatedWhenInitiatePaymentOrder() {
		// Given
		PaymentOrderInitiationRequest request = new PaymentOrderInitiationRequest();
		CreatePaymentOrderCommand command = new CreatePaymentOrderCommand(
				"E2E-123", "ES123", "ES456", Currency.getInstance("EUR"),
				BigDecimal.valueOf(100.0), LocalDate.now(), "Test"
		);
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id("PO-123")
				.status(PaymentOrderStatus.INITIATED)
				.build();
		PaymentOrderResource resource = new PaymentOrderResource();

		when(paymentOrderRestMapper.toCommand(any())).thenReturn(command);
		when(createPaymentOrderUseCase.execute(any())).thenReturn(paymentOrder);
		when(paymentOrderRestMapper.toResource(any())).thenReturn(resource);

		// When
		ResponseEntity<PaymentOrderResource> response = paymentOrdersController.initiatePaymentOrder(request);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		verify(paymentOrderRestMapper).toCommand(request);
		verify(createPaymentOrderUseCase).execute(command);
		verify(paymentOrderRestMapper).toResource(paymentOrder);
	}

	@Test
	void shouldReturnOkWhenGetPaymentOrderById() {
		// Given
		String id = "PO-123";
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id(id)
				.status(PaymentOrderStatus.INITIATED)
				.build();
		PaymentOrderResource resource = new PaymentOrderResource();

		when(getPaymentOrderUseCase.getById(id)).thenReturn(paymentOrder);
		when(paymentOrderRestMapper.toResource(paymentOrder)).thenReturn(resource);

		// When
		ResponseEntity<PaymentOrderResource> response = paymentOrdersController.getPaymentOrderById(id);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		verify(getPaymentOrderUseCase).getById(id);
		verify(paymentOrderRestMapper).toResource(paymentOrder);
	}

	@Test
	void shouldReturnNotFoundWhenGetPaymentOrderByIdNotFound() {
		// Given
		String id = "PO-999";
		when(getPaymentOrderUseCase.getById(id))
				.thenThrow(new com.hiberus.payment_initiation.shared.exception.DomainException("Not found"));

		// When/Then
		assertThatThrownBy(() -> paymentOrdersController.getPaymentOrderById(id))
				.isInstanceOf(ResponseStatusException.class)
				.satisfies(exception -> {
					ResponseStatusException ex = (ResponseStatusException) exception;
					assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
				});
	}

	@Test
	void shouldReturnOkWhenGetPaymentOrderStatusById() {
		// Given
		String id = "PO-123";
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id(id)
				.status(PaymentOrderStatus.PENDING)
				.updatedAt(OffsetDateTime.now())
				.build();
		PaymentOrderStatusResource resource = new PaymentOrderStatusResource();

		when(getPaymentOrderUseCase.getById(id)).thenReturn(paymentOrder);
		when(paymentOrderRestMapper.toStatusResource(paymentOrder)).thenReturn(resource);

		// When
		ResponseEntity<PaymentOrderStatusResource> response = paymentOrdersController.getPaymentOrderStatusById(id);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		verify(getPaymentOrderUseCase).getById(id);
		verify(paymentOrderRestMapper).toStatusResource(paymentOrder);
	}
}

