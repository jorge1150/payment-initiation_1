package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import com.hiberus.payment_initiation.shared.exception.DomainException;

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
	 * @return the payment order if found
	 * @throws DomainException if the payment order is not found
	 */
	public PaymentOrder getById(String paymentOrderId) {
		return paymentOrderRepository.findById(paymentOrderId)
				.orElseThrow(() -> new DomainException("Payment order not found: " + paymentOrderId));
	}

	/**
	 * Retrieves the status of a payment order by its identifier.
	 *
	 * @param paymentOrderId the identifier of the payment order
	 * @return the payment order status
	 * @throws DomainException if the payment order is not found
	 */
	public PaymentOrderStatus getStatusById(String paymentOrderId) {
		PaymentOrder paymentOrder = getById(paymentOrderId);
		return paymentOrder.getStatus();
	}
}

