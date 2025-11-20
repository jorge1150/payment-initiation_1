package com.hiberus.payment_initiation.infrastructure.config;

import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import com.hiberus.payment_initiation.infrastructure.persistence.PaymentOrderPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Central location for Spring beans wiring adapters to domain ports.
 */
@Configuration
public class ApplicationConfig {

	@Bean
	public PaymentOrderRepository paymentOrderRepository() {
		return new PaymentOrderPersistenceAdapter();
	}

	@Bean
	public CreatePaymentOrderUseCase createPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		return new CreatePaymentOrderUseCase(paymentOrderRepository);
	}

	@Bean
	public GetPaymentOrderUseCase getPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		return new GetPaymentOrderUseCase(paymentOrderRepository);
	}
}

