package com.hiberus.payment_initiation.infrastructure.soap;

import com.hiberus.payment_initiation.domain.model.PaymentOrderStatus;
import com.hiberus.payment_initiation.domain.port.PaymentOrderStatusProvider;

/**
 * Outbound adapter that will wrap the legacy SOAP client once the WSDL is integrated.
 */
public class LegacyPaymentOrderSoapClient implements PaymentOrderStatusProvider {

	@Override
	public PaymentOrderStatus getCurrentStatus(String paymentOrderId) {
		// TODO: Invoke generated SOAP stubs and translate responses to domain statuses.
		return PaymentOrderStatus.DRAFT;
	}
}

