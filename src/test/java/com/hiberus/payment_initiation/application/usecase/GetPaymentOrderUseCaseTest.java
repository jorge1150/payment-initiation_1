package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import com.hiberus.payment_initiation.shared.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
				.status(PaymentOrderStatus.INITIATED)
				.build();

		when(paymentOrderRepository.findById(paymentOrderId)).thenReturn(Optional.of(paymentOrder));

		// When
		PaymentOrder result = getPaymentOrderUseCase.getById(paymentOrderId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(paymentOrderId);
		verify(paymentOrderRepository).findById(paymentOrderId);
	}

	@Test
	void shouldThrowExceptionWhenPaymentOrderNotFound() {
		// Given
		String paymentOrderId = "PO-999";

		when(paymentOrderRepository.findById(paymentOrderId)).thenReturn(Optional.empty());

		// When/Then
		assertThatThrownBy(() -> getPaymentOrderUseCase.getById(paymentOrderId))
				.isInstanceOf(DomainException.class)
				.hasMessageContaining("Payment order not found");
		verify(paymentOrderRepository).findById(paymentOrderId);
	}

	@Test
	void shouldReturnStatusWhenPaymentOrderFound() {
		// Given
		String paymentOrderId = "PO-123";
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id(paymentOrderId)
				.status(PaymentOrderStatus.PENDING)
				.build();

		when(paymentOrderRepository.findById(paymentOrderId)).thenReturn(Optional.of(paymentOrder));

		// When
		PaymentOrderStatus result = getPaymentOrderUseCase.getStatusById(paymentOrderId);

		// Then
		assertThat(result).isEqualTo(PaymentOrderStatus.PENDING);
		verify(paymentOrderRepository).findById(paymentOrderId);
	}
}

