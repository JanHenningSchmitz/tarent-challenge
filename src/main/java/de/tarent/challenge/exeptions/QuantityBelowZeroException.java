package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class QuantityBelowZeroException extends RuntimeException {

	private static final long serialVersionUID = -6121980453131098708L;

	public QuantityBelowZeroException(int quantity) {
		super("quantity " + quantity + " must be > 0");
	}
}
