package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePaymentOrderUseCaseTest {

	@Mock
	private PaymentOrderRepository paymentOrderRepository;

	private CreatePaymentOrderUseCase createPaymentOrderUseCase;

	@BeforeEach
	void setUp() {
		createPaymentOrderUseCase = new CreatePaymentOrderUseCase(paymentOrderRepository);
	}

	@Test
	void shouldSavePaymentOrderWhenExecuted() {
		// Given
		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id("PO-123")
				.build();
		PaymentOrder savedPaymentOrder = PaymentOrder.builder()
				.id("PO-123")
				.build();

		when(paymentOrderRepository.save(any(PaymentOrder.class))).thenReturn(savedPaymentOrder);

		// When
		PaymentOrder result = createPaymentOrderUseCase.execute(paymentOrder);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo("PO-123");
		verify(paymentOrderRepository).save(paymentOrder);
	}
}

