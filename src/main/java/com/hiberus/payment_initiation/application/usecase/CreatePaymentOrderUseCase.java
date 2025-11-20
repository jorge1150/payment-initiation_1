package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;

/**
 * Application service responsible for orchestrating the creation of a payment order.
 */
public class CreatePaymentOrderUseCase {

	private final PaymentOrderRepository paymentOrderRepository;

	public CreatePaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		this.paymentOrderRepository = paymentOrderRepository;
	}

	/**
	 * Creates a new payment order.
	 *
	 * @param paymentOrder the payment order to create
	 * @return the saved payment order
	 */
	public PaymentOrder execute(PaymentOrder paymentOrder) {
		return paymentOrderRepository.save(paymentOrder);
	}
}

