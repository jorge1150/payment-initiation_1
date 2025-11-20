package com.hiberus.payment_initiation.infrastructure.persistence;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;

import java.util.Optional;

/**
 * Persistence adapter placeholder. Will connect to the chosen data store (JPA, R2DBC, etc.).
 */
public class PaymentOrderPersistenceAdapter implements PaymentOrderRepository {

	@Override
	public PaymentOrder save(PaymentOrder paymentOrder) {
		// TODO: Persist using the selected technology and return the aggregate.
		return paymentOrder;
	}

	@Override
	public Optional<PaymentOrder> findById(String paymentOrderId) {
		// TODO: Retrieve data source entity and map to aggregate.
		return Optional.empty();
	}
}

