package com.hiberus.payment_initiation.infrastructure.persistence;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of PaymentOrderRepository for integration tests.
 * This implementation stores payment orders in a ConcurrentHashMap.
 */
public class InMemoryPaymentOrderRepository implements PaymentOrderRepository {

	private final Map<String, PaymentOrder> storage = new ConcurrentHashMap<>();

	@Override
	public PaymentOrder save(PaymentOrder paymentOrder) {
		storage.put(paymentOrder.getId(), paymentOrder);
		return paymentOrder;
	}

	@Override
	public Optional<PaymentOrder> findById(String paymentOrderId) {
		return Optional.ofNullable(storage.get(paymentOrderId));
	}

	/**
	 * Clears all stored payment orders. Useful for test cleanup.
	 */
	public void clear() {
		storage.clear();
	}
}

