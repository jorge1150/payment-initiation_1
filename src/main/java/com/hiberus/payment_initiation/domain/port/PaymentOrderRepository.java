package com.hiberus.payment_initiation.domain.port;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;

import java.util.Optional;

/**
 * Primary port to persist and retrieve payment orders.
 * Infrastructure adapters (JPA, JDBC, etc.) will implement this interface.
 */
public interface PaymentOrderRepository {

	PaymentOrder save(PaymentOrder paymentOrder);

	Optional<PaymentOrder> findById(String paymentOrderId);
}

