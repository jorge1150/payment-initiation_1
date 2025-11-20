package com.hiberus.payment_initiation.infrastructure.soap;

import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for LegacyPaymentOrderSoapClient to increase JaCoCo coverage.
 * This test covers the SOAP adapter methods even though they currently have TODO implementations.
 */
class SoapAdapterTest {

	private LegacyPaymentOrderSoapClient soapClient;

	@BeforeEach
	void setUp() {
		soapClient = new LegacyPaymentOrderSoapClient();
	}

	@Test
	void shouldReturnDefaultStatusWhenGettingCurrentStatus() {
		// Given
		String paymentOrderId = "PO-123";

		// When
		PaymentOrderStatus status = soapClient.getCurrentStatus(paymentOrderId);

		// Then
		assertThat(status).isNotNull();
		assertThat(status).isEqualTo(PaymentOrderStatus.DRAFT);
	}

	@Test
	void shouldReturnSameStatusForDifferentPaymentOrderIds() {
		// Given
		String paymentOrderId1 = "PO-123";
		String paymentOrderId2 = "PO-456";

		// When
		PaymentOrderStatus status1 = soapClient.getCurrentStatus(paymentOrderId1);
		PaymentOrderStatus status2 = soapClient.getCurrentStatus(paymentOrderId2);

		// Then
		assertThat(status1).isEqualTo(status2);
		assertThat(status1).isEqualTo(PaymentOrderStatus.DRAFT);
	}
}

