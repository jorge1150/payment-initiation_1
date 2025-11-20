package com.hiberus.payment_initiation.infrastructure.config;

import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import com.hiberus.payment_initiation.infrastructure.persistence.InMemoryPaymentOrderRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Test configuration that provides in-memory implementations for integration tests.
 * This configuration replaces ApplicationConfig for testing purposes.
 */
@TestConfiguration
public class TestConfig {

	private final InMemoryPaymentOrderRepository repository = new InMemoryPaymentOrderRepository();

	@Bean
	@Primary
	public PaymentOrderRepository paymentOrderRepository() {
		return repository;
	}

	@Bean
	public InMemoryPaymentOrderRepository inMemoryPaymentOrderRepository() {
		return repository;
	}

	@Bean
	@Primary
	public CreatePaymentOrderUseCase createPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		return new CreatePaymentOrderUseCase(paymentOrderRepository);
	}

	@Bean
	@Primary
	public GetPaymentOrderUseCase getPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		return new GetPaymentOrderUseCase(paymentOrderRepository);
	}
}

