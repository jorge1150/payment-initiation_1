package com.hiberus.payment_initiation.application.usecase;

import com.hiberus.payment_initiation.application.dto.CreatePaymentOrderCommand;
import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.domain.port.PaymentOrderRepository;

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

/**
 * Application service responsible for orchestrating the creation of a payment order.
 */
public class CreatePaymentOrderUseCase {

	private final PaymentOrderRepository paymentOrderRepository;

	public CreatePaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
		this.paymentOrderRepository = paymentOrderRepository;
	}

	/**
	 * Creates a new payment order from a command.
	 *
	 * @param command the command containing payment order data
	 * @return the saved payment order with generated ID and timestamps
	 */
	public PaymentOrder execute(CreatePaymentOrderCommand command) {
		OffsetDateTime now = OffsetDateTime.now();
		String id = generateId();

		PaymentOrder paymentOrder = PaymentOrder.builder()
				.id(id)
				.endToEndId(command.getEndToEndId())
				.debtorAccount(command.getDebtorAccount())
				.creditorAccount(command.getCreditorAccount())
				.currency(command.getCurrency())
				.amount(command.getAmount())
				.status(PaymentOrderStatus.INITIATED)
				.requestedExecutionDate(command.getRequestedExecutionDate())
				.remittanceInformation(command.getRemittanceInformation())
				.createdAt(now)
				.updatedAt(now)
				.build();

		return paymentOrderRepository.save(paymentOrder);
	}

	private String generateId() {
		// TODO: Replace with proper ID generation strategy (e.g., sequence, UUID with prefix)
		return "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
	}
}

