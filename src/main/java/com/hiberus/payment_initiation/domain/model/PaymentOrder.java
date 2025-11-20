package com.hiberus.payment_initiation.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.Objects;

/**
 * Aggregate root representing a payment order inside the BIAN Payment Initiation domain.
 * This model is aligned with the OpenAPI contract but remains framework-agnostic.
 */
public class PaymentOrder {

	private final String id;
	private final String endToEndId;
	private final String debtorAccount;
	private final String creditorAccount;
	private final Currency currency;
	private final BigDecimal amount;
	private final PaymentOrderStatus status;
	private final LocalDate requestedExecutionDate;
	private final String remittanceInformation;
	private final OffsetDateTime createdAt;
	private final OffsetDateTime updatedAt;

	private PaymentOrder(Builder builder) {
		this.id = builder.id;
		this.endToEndId = builder.endToEndId;
		this.debtorAccount = builder.debtorAccount;
		this.creditorAccount = builder.creditorAccount;
		this.currency = builder.currency;
		this.amount = builder.amount;
		this.status = builder.status;
		this.requestedExecutionDate = builder.requestedExecutionDate;
		this.remittanceInformation = builder.remittanceInformation;
		this.createdAt = builder.createdAt;
		this.updatedAt = builder.updatedAt;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getId() {
		return id;
	}

	public String getEndToEndId() {
		return endToEndId;
	}

	public String getDebtorAccount() {
		return debtorAccount;
	}

	public String getCreditorAccount() {
		return creditorAccount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public PaymentOrderStatus getStatus() {
		return status;
	}

	public LocalDate getRequestedExecutionDate() {
		return requestedExecutionDate;
	}

	public String getRemittanceInformation() {
		return remittanceInformation;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public static final class Builder {
		private String id;
		private String endToEndId;
		private String debtorAccount;
		private String creditorAccount;
		private Currency currency;
		private BigDecimal amount;
		private PaymentOrderStatus status = PaymentOrderStatus.INITIATED;
		private LocalDate requestedExecutionDate;
		private String remittanceInformation;
		private OffsetDateTime createdAt;
		private OffsetDateTime updatedAt;

		private Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder endToEndId(String endToEndId) {
			this.endToEndId = endToEndId;
			return this;
		}

		public Builder debtorAccount(String debtorAccount) {
			this.debtorAccount = debtorAccount;
			return this;
		}

		public Builder creditorAccount(String creditorAccount) {
			this.creditorAccount = creditorAccount;
			return this;
		}

		public Builder currency(Currency currency) {
			this.currency = currency;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder status(PaymentOrderStatus status) {
			this.status = status;
			return this;
		}

		public Builder requestedExecutionDate(LocalDate requestedExecutionDate) {
			this.requestedExecutionDate = requestedExecutionDate;
			return this;
		}

		public Builder remittanceInformation(String remittanceInformation) {
			this.remittanceInformation = remittanceInformation;
			return this;
		}

		public Builder createdAt(OffsetDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder updatedAt(OffsetDateTime updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

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

