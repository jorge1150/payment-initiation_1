package com.hiberus.payment_initiation.infrastructure.rest.mapper;

import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.application.dto.CreatePaymentOrderCommand;
import com.hiberus.payment_initiation.generated.model.AccountIdentification;
import com.hiberus.payment_initiation.generated.model.Amount;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import com.hiberus.payment_initiation.generated.model.PaymentOrderResource;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Currency;

/**
 * Mapper between REST DTOs (generated from OpenAPI) and domain models.
 * This class isolates the translation between the external API contract and the internal domain.
 */
@Component
public class PaymentOrderRestMapper {

	/**
	 * Maps a PaymentOrderInitiationRequest to a CreatePaymentOrderCommand.
	 *
	 * @param request the REST request DTO
	 * @return the command for the use case
	 */
	public CreatePaymentOrderCommand toCommand(PaymentOrderInitiationRequest request) {
		String debtorAccount = extractAccountNumber(request.getDebtorAccount());
		String creditorAccount = extractAccountNumber(request.getCreditorAccount());
		Currency currency = Currency.getInstance(request.getAmount().getCurrency());
		BigDecimal amount = BigDecimal.valueOf(request.getAmount().getAmount());
		LocalDate requestedExecutionDate = request.getRequestedExecutionDate();

		return new CreatePaymentOrderCommand(
				request.getEndToEndId(),
				debtorAccount,
				creditorAccount,
				currency,
				amount,
				requestedExecutionDate,
				request.getRemittanceInformation()
		);
	}

	/**
	 * Maps a domain PaymentOrder to a PaymentOrderResource.
	 *
	 * @param paymentOrder the domain model
	 * @return the REST resource DTO
	 */
	public PaymentOrderResource toResource(PaymentOrder paymentOrder) {
		PaymentOrderResource resource = new PaymentOrderResource();
		resource.setId(paymentOrder.getId());
		resource.setEndToEndId(paymentOrder.getEndToEndId());
		resource.setStatus(toGeneratedStatus(paymentOrder.getStatus()));

		// Map debtor account
		AccountIdentification debtorAccount = new AccountIdentification();
		debtorAccount.setAccountNumber(paymentOrder.getDebtorAccount());
		resource.setDebtorAccount(debtorAccount);

		// Map creditor account
		AccountIdentification creditorAccount = new AccountIdentification();
		creditorAccount.setAccountNumber(paymentOrder.getCreditorAccount());
		resource.setCreditorAccount(creditorAccount);

		// Map amount
		Amount amount = new Amount();
		amount.setCurrency(paymentOrder.getCurrency().getCurrencyCode());
		amount.setAmount(paymentOrder.getAmount().doubleValue());
		resource.setAmount(amount);

		resource.setRequestedExecutionDate(paymentOrder.getRequestedExecutionDate());
		resource.setRemittanceInformation(paymentOrder.getRemittanceInformation());
		resource.setCreationDateTime(paymentOrder.getCreatedAt());
		resource.setLastUpdatedDateTime(paymentOrder.getUpdatedAt());

		return resource;
	}

	/**
	 * Maps a domain PaymentOrder to a PaymentOrderStatusResource.
	 *
	 * @param paymentOrder the domain model
	 * @return the status resource DTO
	 */
	public PaymentOrderStatusResource toStatusResource(PaymentOrder paymentOrder) {
		PaymentOrderStatusResource resource = new PaymentOrderStatusResource();
		resource.setId(paymentOrder.getId());
		resource.setStatus(toGeneratedStatus(paymentOrder.getStatus()));
		resource.setLastUpdatedDateTime(paymentOrder.getUpdatedAt());
		// statusReason is optional and can be set later if needed
		return resource;
	}

	/**
	 * Maps a domain PaymentOrderStatus to the generated enum.
	 *
	 * @param status the domain status
	 * @return the generated status enum
	 */
	private com.hiberus.payment_initiation.generated.model.PaymentOrderStatus toGeneratedStatus(PaymentOrderStatus status) {
		return switch (status) {
			case INITIATED -> com.hiberus.payment_initiation.generated.model.PaymentOrderStatus.INITIATED;
			case PENDING -> com.hiberus.payment_initiation.generated.model.PaymentOrderStatus.PENDING;
			case ACCEPTED -> com.hiberus.payment_initiation.generated.model.PaymentOrderStatus.ACCEPTED;
			case REJECTED -> com.hiberus.payment_initiation.generated.model.PaymentOrderStatus.REJECTED;
			case SETTLED -> com.hiberus.payment_initiation.generated.model.PaymentOrderStatus.SETTLED;
		};
	}

	/**
	 * Extracts the account number from an AccountIdentification.
	 * If the AccountIdentification is null, returns null.
	 *
	 * @param accountIdentification the account identification DTO
	 * @return the account number string
	 */
	private String extractAccountNumber(AccountIdentification accountIdentification) {
		if (accountIdentification == null) {
			return null;
		}
		return accountIdentification.getAccountNumber();
	}
}

