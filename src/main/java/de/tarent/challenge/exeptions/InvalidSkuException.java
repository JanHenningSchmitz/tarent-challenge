package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidSkuException extends RuntimeException {

	private static final long serialVersionUID = -6279623726325957574L;

	public InvalidSkuException() {
		super("sku must not be blank");
	}
}
