package com.hiberus.payment_initiation.infrastructure.rest;

import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.domain.model.PaymentOrder;
import com.hiberus.payment_initiation.generated.api.PaymentOrdersApi;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import com.hiberus.payment_initiation.generated.model.PaymentOrderResource;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource;
import com.hiberus.payment_initiation.infrastructure.rest.mapper.PaymentOrderRestMapper;
import com.hiberus.payment_initiation.shared.exception.DomainException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

/**
 * REST adapter that implements the contract generated from OpenAPI and delegates to application use cases.
 */
@RestController
public class PaymentOrdersController implements PaymentOrdersApi {

	private static final Logger log = LoggerFactory.getLogger(PaymentOrdersController.class);

	private final CreatePaymentOrderUseCase createPaymentOrderUseCase;
	private final GetPaymentOrderUseCase getPaymentOrderUseCase;
	private final PaymentOrderRestMapper mapper;

	public PaymentOrdersController(CreatePaymentOrderUseCase createPaymentOrderUseCase,
			GetPaymentOrderUseCase getPaymentOrderUseCase,
			PaymentOrderRestMapper mapper) {
		this.createPaymentOrderUseCase = createPaymentOrderUseCase;
		this.getPaymentOrderUseCase = getPaymentOrderUseCase;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<PaymentOrderResource> initiatePaymentOrder(
			@Valid PaymentOrderInitiationRequest paymentOrderInitiationRequest) {
		log.info("Received initiatePaymentOrder request: endToEndId={}, debtorAccount={}, creditorAccount={}, amount={}",
				paymentOrderInitiationRequest.getEndToEndId(),
				paymentOrderInitiationRequest.getDebtorAccount() != null
						? paymentOrderInitiationRequest.getDebtorAccount().getAccountNumber()
						: null,
				paymentOrderInitiationRequest.getCreditorAccount() != null
						? paymentOrderInitiationRequest.getCreditorAccount().getAccountNumber()
						: null,
				paymentOrderInitiationRequest.getAmount() != null
						? paymentOrderInitiationRequest.getAmount().getCurrency() + " "
								+ paymentOrderInitiationRequest.getAmount().getAmount()
						: null);

		try {
			// Map request to command
			var command = mapper.toCommand(paymentOrderInitiationRequest);

			// Execute use case
			PaymentOrder paymentOrder = createPaymentOrderUseCase.execute(command);
			log.info("Payment order created successfully: id={}, status={}", paymentOrder.getId(),
					paymentOrder.getStatus());

			// Map domain to resource
			PaymentOrderResource resource = mapper.toResource(paymentOrder);

			// Return 201 Created with Location header
			URI location = URI.create("/payment-initiation/payment-orders/" + paymentOrder.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.location(location)
					.body(resource);
		} catch (Exception e) {
			log.error("Error creating payment order", e);
			// TODO: Add proper exception handling with @ControllerAdvice
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating payment order", e);
		}
	}

	@Override
	public ResponseEntity<PaymentOrderResource> getPaymentOrderById(String id) {
		log.info("Received getPaymentOrderById request: id={}", id);
		try {
			PaymentOrder paymentOrder = getPaymentOrderUseCase.getById(id);
			log.info("Payment order found: id={}, status={}", paymentOrder.getId(), paymentOrder.getStatus());
			PaymentOrderResource resource = mapper.toResource(paymentOrder);
			return ResponseEntity.ok(resource);
		} catch (DomainException e) {
			log.warn("Payment order not found: id={}", id);
			// TODO: Replace with @ControllerAdvice for consistent error handling
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error retrieving payment order: id={}", id, e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving payment order", e);
		}
	}

	@Override
	public ResponseEntity<PaymentOrderStatusResource> getPaymentOrderStatusById(String id) {
		log.info("Received getPaymentOrderStatusById request: id={}", id);
		try {
			PaymentOrder paymentOrder = getPaymentOrderUseCase.getById(id);
			log.info("Payment order status retrieved: id={}, status={}", paymentOrder.getId(),
					paymentOrder.getStatus());
			PaymentOrderStatusResource resource = mapper.toStatusResource(paymentOrder);
			return ResponseEntity.ok(resource);
		} catch (DomainException e) {
			log.warn("Payment order not found for status request: id={}", id);
			// TODO: Replace with @ControllerAdvice for consistent error handling
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error retrieving payment order status: id={}", id, e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving payment order status",
					e);
		}
	}
}

