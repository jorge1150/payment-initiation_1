package com.hiberus.payment_initiation.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.Objects;

/**
 * Aggregate root representing a payment order inside the BIAN Payment Initiation domain.
 * TODO: Refine attributes once the OpenAPI contract and legacy SOAP schemas are finalized.
 */
public class PaymentOrder {

	private final String id;
	private final String debtorAccount;
	private final String creditorAccount;
	private final Currency currency;
	private final BigDecimal amount;
	private final PaymentOrderStatus status;
	private final OffsetDateTime requestedExecutionDate;

	private PaymentOrder(Builder builder) {
		this.id = builder.id;
		this.debtorAccount = builder.debtorAccount;
		this.creditorAccount = builder.creditorAccount;
		this.currency = builder.currency;
		this.amount = builder.amount;
		this.status = builder.status;
		this.requestedExecutionDate = builder.requestedExecutionDate;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getId() {
		return id;
	}

	public PaymentOrderStatus getStatus() {
		return status;
	}

	public static final class Builder {
		private String id;
		private String debtorAccount;
		private String creditorAccount;
		private Currency currency;
		private BigDecimal amount;
		private PaymentOrderStatus status = PaymentOrderStatus.DRAFT;
		private OffsetDateTime requestedExecutionDate;

		private Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		// TODO: add remaining builder setters and validation logic when needed.

		public PaymentOrder build() {
			return new PaymentOrder(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PaymentOrder that = (PaymentOrder) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

