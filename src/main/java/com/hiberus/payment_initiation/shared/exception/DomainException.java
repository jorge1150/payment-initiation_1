package com.hiberus.payment_initiation.shared.exception;

/**
 * Base unchecked exception for domain-specific errors.
 * TODO: Add error codes / problem details aligned with API guidelines.
 */
public class DomainException extends RuntimeException {

	public DomainException(String message) {
		super(message);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
}

