package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidProductNameException extends RuntimeException {

	private static final long serialVersionUID = 5083613650640201101L;

	public InvalidProductNameException() {
		super("name must not be blank");
	}
}
