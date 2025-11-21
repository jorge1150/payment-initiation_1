package com.hiberus.payment_initiation.infrastructure.persistence;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory persistence adapter for PaymentOrder.
 * This implementation stores payment orders in a ConcurrentHashMap.
 * 
 * TODO: Replace with a real persistence implementation (JPA, R2DBC, etc.) when ready.
 */
public class PaymentOrderPersistenceAdapter implements PaymentOrderRepository {

	private static final Logger log = LoggerFactory.getLogger(PaymentOrderPersistenceAdapter.class);

	// In-memory storage using the payment order ID as the key
	private final Map<String, PaymentOrder> storage = new ConcurrentHashMap<>();

	@Override
	public PaymentOrder save(PaymentOrder paymentOrder) {
		log.debug("Saving payment order: id={}, status={}", paymentOrder.getId(), paymentOrder.getStatus());
		storage.put(paymentOrder.getId(), paymentOrder);
		log.debug("Payment order saved successfully: id={}, total stored={}", paymentOrder.getId(), storage.size());
		return paymentOrder;
	}

	@Override
	public Optional<PaymentOrder> findById(String paymentOrderId) {
		log.debug("Finding payment order by id={}", paymentOrderId);
		PaymentOrder found = storage.get(paymentOrderId);
		if (found != null) {
			log.debug("Payment order found: id={}, status={}", found.getId(), found.getStatus());
		} else {
			log.debug("Payment order not found: id={}, total stored={}", paymentOrderId, storage.size());
		}
		return Optional.ofNullable(found);
	}
}

