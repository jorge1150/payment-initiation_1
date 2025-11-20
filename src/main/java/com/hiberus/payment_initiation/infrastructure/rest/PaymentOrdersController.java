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
	public ResponseEntity<PaymentOrderResource> initiatePaymentOrder(PaymentOrderInitiationRequest paymentOrderInitiationRequest) {
		try {
			// Map request to command
			var command = mapper.toCommand(paymentOrderInitiationRequest);

			// Execute use case
			PaymentOrder paymentOrder = createPaymentOrderUseCase.execute(command);

			// Map domain to resource
			PaymentOrderResource resource = mapper.toResource(paymentOrder);

			// Return 201 Created with Location header
			URI location = URI.create("/payment-initiation/payment-orders/" + paymentOrder.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.location(location)
					.body(resource);
		} catch (Exception e) {
			// TODO: Add proper exception handling with @ControllerAdvice
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating payment order", e);
		}
	}

	@Override
	public ResponseEntity<PaymentOrderResource> getPaymentOrderById(String id) {
		try {
			PaymentOrder paymentOrder = getPaymentOrderUseCase.getById(id);
			PaymentOrderResource resource = mapper.toResource(paymentOrder);
			return ResponseEntity.ok(resource);
		} catch (DomainException e) {
			// TODO: Replace with @ControllerAdvice for consistent error handling
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving payment order", e);
		}
	}

	@Override
	public ResponseEntity<PaymentOrderStatusResource> getPaymentOrderStatusById(String id) {
		try {
			PaymentOrder paymentOrder = getPaymentOrderUseCase.getById(id);
			PaymentOrderStatusResource resource = mapper.toStatusResource(paymentOrder);
			return ResponseEntity.ok(resource);
		} catch (DomainException e) {
			// TODO: Replace with @ControllerAdvice for consistent error handling
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving payment order status", e);
		}
	}
}

