package com.hiberus.payment_initiation.infrastructure.rest;

import com.hiberus.payment_initiation.generated.model.AccountIdentification;
import com.hiberus.payment_initiation.generated.model.Amount;
import com.hiberus.payment_initiation.generated.model.PaymentOrderInitiationRequest;
import com.hiberus.payment_initiation.generated.model.PaymentOrderResource;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.generated.model.PaymentOrderStatusResource;
import com.hiberus.payment_initiation.infrastructure.config.TestConfig;
import com.hiberus.payment_initiation.infrastructure.persistence.InMemoryPaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Payment Initiation REST API using WebTestClient.
 * These tests validate the contract defined in OpenAPI and ensure the API behaves correctly.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@Import(TestConfig.class)
class PaymentInitiationIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private InMemoryPaymentOrderRepository repository;

	@BeforeEach
	void setUp() {
		// Each test starts with a clean state
		// Clear the in-memory repository before each test
		if (repository != null) {
			repository.clear();
		}
	}

	@Test
	void shouldCreatePaymentOrderSuccessfully() {
		// Given
		PaymentOrderInitiationRequest request = createValidPaymentOrderRequest();

		// When & Then
		webTestClient.post()
				.uri("/payment-initiation/payment-orders")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().exists("Location")
				.expectBody(PaymentOrderResource.class)
				.value(paymentOrder -> {
					assertThat(paymentOrder.getId()).isNotNull();
					assertThat(paymentOrder.getId()).isNotEmpty();
					assertThat(paymentOrder.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
					assertThat(paymentOrder.getEndToEndId()).isEqualTo(request.getEndToEndId());
					assertThat(paymentOrder.getAmount()).isNotNull();
					assertThat(paymentOrder.getAmount().getCurrency()).isEqualTo(request.getAmount().getCurrency());
					assertThat(paymentOrder.getAmount().getAmount()).isEqualTo(request.getAmount().getAmount());
					assertThat(paymentOrder.getDebtorAccount()).isNotNull();
					assertThat(paymentOrder.getDebtorAccount().getAccountNumber())
							.isEqualTo(request.getDebtorAccount().getAccountNumber());
					assertThat(paymentOrder.getCreditorAccount()).isNotNull();
					assertThat(paymentOrder.getCreditorAccount().getAccountNumber())
							.isEqualTo(request.getCreditorAccount().getAccountNumber());
					assertThat(paymentOrder.getRequestedExecutionDate())
							.isEqualTo(request.getRequestedExecutionDate());
					assertThat(paymentOrder.getRemittanceInformation())
							.isEqualTo(request.getRemittanceInformation());
					assertThat(paymentOrder.getCreationDateTime()).isNotNull();
					assertThat(paymentOrder.getLastUpdatedDateTime()).isNotNull();
				});
	}

	@Test
	void shouldRetrievePaymentOrderById() {
		// Given - Create a payment order first
		PaymentOrderInitiationRequest request = createValidPaymentOrderRequest();
		PaymentOrderResource createdOrder = webTestClient.post()
				.uri("/payment-initiation/payment-orders")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(PaymentOrderResource.class)
				.returnResult()
				.getResponseBody();

		assertThat(createdOrder).isNotNull();
		String orderId = createdOrder.getId();

		// When & Then - Retrieve the created order
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}", orderId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(PaymentOrderResource.class)
				.value(paymentOrder -> {
					assertThat(paymentOrder.getId()).isEqualTo(orderId);
					assertThat(paymentOrder.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
					assertThat(paymentOrder.getEndToEndId()).isEqualTo(request.getEndToEndId());
					assertThat(paymentOrder.getAmount()).isNotNull();
					assertThat(paymentOrder.getAmount().getCurrency())
							.isEqualTo(request.getAmount().getCurrency());
					assertThat(paymentOrder.getAmount().getAmount())
							.isEqualTo(request.getAmount().getAmount());
				});
	}

	@Test
	void shouldRetrievePaymentOrderStatusById() {
		// Given - Create a payment order first
		PaymentOrderInitiationRequest request = createValidPaymentOrderRequest();
		PaymentOrderResource createdOrder = webTestClient.post()
				.uri("/payment-initiation/payment-orders")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(PaymentOrderResource.class)
				.returnResult()
				.getResponseBody();

		assertThat(createdOrder).isNotNull();
		String orderId = createdOrder.getId();

		// When & Then - Retrieve the status
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}/status", orderId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(PaymentOrderStatusResource.class)
				.value(statusResource -> {
					assertThat(statusResource.getId()).isEqualTo(orderId);
					assertThat(statusResource.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
					assertThat(statusResource.getLastUpdatedDateTime()).isNotNull();
				});
	}

	@Test
	void shouldReturnNotFoundWhenPaymentOrderDoesNotExist() {
		// Given
		String nonExistentId = "NON_EXISTENT_ID";

		// When & Then
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}", nonExistentId)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void shouldReturnNotFoundWhenPaymentOrderStatusDoesNotExist() {
		// Given
		String nonExistentId = "NON_EXISTENT_ID";

		// When & Then
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}/status", nonExistentId)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void shouldCompleteFullFlowPostGetGetStatus() {
		// Given - Create a payment order
		PaymentOrderInitiationRequest request = createValidPaymentOrderRequest();
		PaymentOrderResource createdOrder = webTestClient.post()
				.uri("/payment-initiation/payment-orders")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(PaymentOrderResource.class)
				.returnResult()
				.getResponseBody();

		assertThat(createdOrder).isNotNull();
		String orderId = createdOrder.getId();
		assertThat(orderId).isNotNull().startsWith("PO-");

		// When & Then - Retrieve the created order by ID
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}", orderId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(PaymentOrderResource.class)
				.value(paymentOrder -> {
					assertThat(paymentOrder.getId()).isEqualTo(orderId);
					assertThat(paymentOrder.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
					assertThat(paymentOrder.getEndToEndId()).isEqualTo(request.getEndToEndId());
				});

		// When & Then - Retrieve the status of the created order
		webTestClient.get()
				.uri("/payment-initiation/payment-orders/{id}/status", orderId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(PaymentOrderStatusResource.class)
				.value(statusResource -> {
					assertThat(statusResource.getId()).isEqualTo(orderId);
					assertThat(statusResource.getStatus()).isEqualTo(PaymentOrderStatus.INITIATED);
					assertThat(statusResource.getLastUpdatedDateTime()).isNotNull();
				});
	}

	/**
	 * Creates a valid PaymentOrderInitiationRequest for testing.
	 *
	 * @return a valid request object
	 */
	private PaymentOrderInitiationRequest createValidPaymentOrderRequest() {
		PaymentOrderInitiationRequest request = new PaymentOrderInitiationRequest();

		// Debtor account
		AccountIdentification debtorAccount = new AccountIdentification();
		debtorAccount.setAccountNumber("ES9121000418450200051332");
		request.setDebtorAccount(debtorAccount);

		// Creditor account
		AccountIdentification creditorAccount = new AccountIdentification();
		creditorAccount.setAccountNumber("ES9800550000000000000000");
		request.setCreditorAccount(creditorAccount);

		// Amount
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setAmount(100.50);
		request.setAmount(amount);

		// Other fields
		request.setRequestedExecutionDate(LocalDate.now().plusDays(1));
		request.setRemittanceInformation("Test payment order");
		request.setEndToEndId("E2E-TEST-001");

		return request;
	}
}

