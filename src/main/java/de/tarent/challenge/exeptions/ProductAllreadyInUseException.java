package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ProductAllreadyInUseException.MESSAGE)
public class ProductAllreadyInUseException extends RuntimeException {

	public static final String MESSAGE = "sku allready in use.";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -8576089012741784390L;

	public ProductAllreadyInUseException(String sku) {
		super("sku '" + sku + "' allready in use.");
	}
}
