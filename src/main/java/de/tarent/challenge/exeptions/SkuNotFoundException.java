package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkuNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8576089012741784390L;

	public SkuNotFoundException(String sku) {
		super("could not find sku '" + sku + "'.");
	}
}