package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPaymentOrderUseCaseTest {

	@Mock
	private PaymentOrderRepository paymentOrderRepository;

	private GetPaymentOrderUseCase getPaymentOrderUseCase;

	@BeforeEach
	void setUp() {
		getPaymentOrderUseCase = new GetPaymentOrderUseCase(paymentOrderRepository);
	}

	@Test
	void shouldReturnPaymentOrderWhenFound() {
		// Given
		String paymentOrderId = "PO-123";
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id(paymentOrderId)
				.build();

		when(paymentOrderRepository.findById(paymentOrderId)).thenReturn(Optional.of(paymentOrder));

		// When
		Optional<PaymentOrder> result = getPaymentOrderUseCase.execute(paymentOrderId);

		// Then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(paymentOrderId);
		verify(paymentOrderRepository).findById(paymentOrderId);
	}

	@Test
	void shouldReturnEmptyWhenPaymentOrderNotFound() {
		// Given
		String paymentOrderId = "PO-999";

		when(paymentOrderRepository.findById(paymentOrderId)).thenReturn(Optional.empty());

		// When
		Optional<PaymentOrder> result = getPaymentOrderUseCase.execute(paymentOrderId);

		// Then
		assertThat(result).isEmpty();
		verify(paymentOrderRepository).findById(paymentOrderId);
	}
}

