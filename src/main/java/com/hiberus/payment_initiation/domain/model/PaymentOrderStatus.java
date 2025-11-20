package com.hiberus.payment_initiation.domain.model;

/**
 * Lifecycle states of a Payment Order aligned with BIAN Payment Initiation / PaymentOrder.
 * These values match the OpenAPI contract enum.
 */
public enum PaymentOrderStatus {
	INITIATED,
	PENDING,
	ACCEPTED,
	REJECTED,
	SETTLED
}

