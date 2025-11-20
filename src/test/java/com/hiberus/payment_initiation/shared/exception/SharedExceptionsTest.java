package com.hiberus.payment_initiation.shared.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for shared exception classes to increase JaCoCo coverage.
 * These tests verify that exception constructors work correctly.
 */
class SharedExceptionsTest {

	@Test
	void shouldCreateDomainExceptionWithMessage() {
		// Given
		String message = "Test error message";

		// When
		DomainException exception = new DomainException(message);

		// Then
		assertThat(exception).isNotNull();
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isNull();
	}

	@Test
	void shouldCreateDomainExceptionWithMessageAndCause() {
		// Given
		String message = "Test error message";
		Throwable cause = new IllegalArgumentException("Root cause");

		// When
		DomainException exception = new DomainException(message, cause);

		// Then
		assertThat(exception).isNotNull();
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
	}

	@Test
	void shouldThrowDomainExceptionWhenThrown() {
		// Given
		String message = "Exception message";

		// When/Then
		assertThatThrownBy(() -> {
			throw new DomainException(message);
		})
				.isInstanceOf(DomainException.class)
				.hasMessage(message);
	}
}

