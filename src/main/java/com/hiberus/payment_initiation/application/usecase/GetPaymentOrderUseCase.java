package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;

import java.util.Optional;

/**
 * Application service for retrieving a payment order aggregate by identifier.
 */
public class GetPaymentOrderUseCase {

	public Optional<PaymentOrder> execute(String paymentOrderId) {
		// TODO: Delegate to PaymentOrderRepository and map to DTOs when available.
		return Optional.empty();
	}
}

