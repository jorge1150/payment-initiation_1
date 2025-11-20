package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;

import java.util.Optional;

/**
 * Application service for retrieving a payment order aggregate by identifier.
 */
public class GetPaymentOrderUseCase {

	private final PaymentOrderRepository paymentOrderRepository;

	public GetPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		this.paymentOrderRepository = paymentOrderRepository;
	}

	/**
	 * Retrieves a payment order by its identifier.
	 *
	 * @param paymentOrderId the identifier of the payment order
	 * @return an Optional containing the payment order if found, empty otherwise
	 */
	public Optional<PaymentOrder> execute(String paymentOrderId) {
		return paymentOrderRepository.findById(paymentOrderId);
	}
}

