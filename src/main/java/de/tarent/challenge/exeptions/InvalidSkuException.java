package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason=InvalidSkuException.MESSAGE)
public class InvalidSkuException extends RuntimeException {

	public static final String MESSAGE = "sku must not be blank";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;
	
	private static final long serialVersionUID = -6279623726325957574L;

	public InvalidSkuException() {
		super("sku must not be blank");
	}
}
