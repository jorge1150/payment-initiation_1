package com.hiberus.payment_initiation.infrastructure.rest;

import com.hiberus.payment_initiation.application.usecase.CreatePaymentOrderUseCase;
import com.hiberus.payment_initiation.application.usecase.GetPaymentOrderUseCase;
import com.hiberus.payment_initiation.generated.api.PaymentOrdersApi;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import com.hiberus.payment_initiation.generated.model.PaymentOrderResource;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST adapter that implements the contract generated from OpenAPI and delegates to application use cases.
 */
@RestController
public class PaymentOrdersController implements PaymentOrdersApi {

	private final CreatePaymentOrderUseCase createPaymentOrderUseCase;
	private final GetPaymentOrderUseCase getPaymentOrderUseCase;

	public PaymentOrdersController(CreatePaymentOrderUseCase createPaymentOrderUseCase,
			GetPaymentOrderUseCase getPaymentOrderUseCase) {
		this.createPaymentOrderUseCase = createPaymentOrderUseCase;
		this.getPaymentOrderUseCase = getPaymentOrderUseCase;
	}

	@Override
	public ResponseEntity<PaymentOrderResource> initiatePaymentOrder(PaymentOrderInitiationRequest paymentOrderInitiationRequest) {
		// TODO: map PaymentOrderInitiationRequest -> domain command and delegate to createPaymentOrderUseCase.
		// TODO: map domain response -> PaymentOrderResource.
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<PaymentOrderResource> getPaymentOrderById(String id) {
		// TODO: delegate to getPaymentOrderUseCase and map result -> PaymentOrderResource.
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<PaymentOrderStatusResource> getPaymentOrderStatusById(String id) {
		// TODO: delegate to getPaymentOrderUseCase and map result -> PaymentOrderStatusResource.
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}

