package com.hiberus.payment_initiation.domain.port;

import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;

/**
 * Secondary port for querying external systems (e.g., legacy SOAP) about the current status of a payment order.
 */
public interface PaymentOrderStatusProvider {

	PaymentOrderStatus getCurrentStatus(String paymentOrderId);
}

