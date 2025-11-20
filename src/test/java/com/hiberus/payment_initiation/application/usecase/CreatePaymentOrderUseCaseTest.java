package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.application.dto.CreatePaymentOrderCommand;
import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Currency;

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
		CreatePaymentOrderCommand command = new CreatePaymentOrderCommand(
				"E2E-123",
				"ES1234567890123456789012",
				"ES9876543210987654321098",
				Currency.getInstance("EUR"),
				BigDecimal.valueOf(100.50),
				LocalDate.now(),
				"Test payment"
		);

		PaymentOrder savedPaymentOrder = PaymentOrder.builder()
				.id("PO-12345678")
				.endToEndId("E2E-123")
				.debtorAccount("ES1234567890123456789012")
				.creditorAccount("ES9876543210987654321098")
				.currency(Currency.getInstance("EUR"))
				.amount(BigDecimal.valueOf(100.50))
				.status(PaymentOrderStatus.INITIATED)
				.requestedExecutionDate(LocalDate.now())
				.remittanceInformation("Test payment")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.build();

		when(paymentOrderRepository.save(any(PaymentOrder.class))).thenReturn(savedPaymentOrder);

		// When
		PaymentOrder result = createPaymentOrderUseCase.execute(command);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo("PO-12345678");
		assertThat(result.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
		verify(paymentOrderRepository).save(any(PaymentOrder.class));
	}
}

