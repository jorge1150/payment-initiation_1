package com.hiberus.payment_initiation.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Command object for creating a payment order.
 * This DTO is used to pass data from the REST layer to the use case.
 */
public class CreatePaymentOrderCommand {

	private final String endToEndId;
	private final String debtorAccount;
	private final String creditorAccount;
	private final Currency currency;
	private final BigDecimal amount;
	private final LocalDate requestedExecutionDate;
	private final String remittanceInformation;

	public CreatePaymentOrderCommand(String endToEndId, String debtorAccount, String creditorAccount,
			Currency currency, BigDecimal amount, LocalDate requestedExecutionDate, String remittanceInformation) {
		this.endToEndId = endToEndId;
		this.debtorAccount = debtorAccount;
		this.creditorAccount = creditorAccount;
		this.currency = currency;
		this.amount = amount;
		this.requestedExecutionDate = requestedExecutionDate;
		this.remittanceInformation = remittanceInformation;
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

	public LocalDate getRequestedExecutionDate() {
		return requestedExecutionDate;
	}

	public String getRemittanceInformation() {
		return remittanceInformation;
	}
}

