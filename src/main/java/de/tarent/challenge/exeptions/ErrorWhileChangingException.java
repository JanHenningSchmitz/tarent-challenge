package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErrorWhileChangingException extends RuntimeException {

	private static final long serialVersionUID = -2505426008886377870L;

	public ErrorWhileChangingException(String sku) {
		super("could not change sku '" + sku + "'.");
	}
}