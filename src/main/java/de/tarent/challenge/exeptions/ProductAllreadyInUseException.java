package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductAllreadyInUseException extends RuntimeException {

	private static final long serialVersionUID = -8576089012741784390L;

	public ProductAllreadyInUseException(String sku) {
		super("sku '" + sku + "' allready in use.");
	}
}
