package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CouldNotDeleteBySkuException extends RuntimeException {

	private static final long serialVersionUID = 5146302715583946775L;

	public CouldNotDeleteBySkuException(String sku) {
		super("could not delete sku '" + sku + "'.");
	}
}