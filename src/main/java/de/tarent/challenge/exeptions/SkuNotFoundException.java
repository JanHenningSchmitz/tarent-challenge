package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason=SkuNotFoundException.MESSAGE)
public class SkuNotFoundException extends RuntimeException {
	
	public static final String MESSAGE = "could not find sku";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -8576089012741784390L;

	public SkuNotFoundException(String sku) {
		super("could not find sku '" + sku + "'.");
	}
}