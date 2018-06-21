package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = InvalidProductNameException.MESSAGE)
public class InvalidProductNameException extends RuntimeException {

	public static final String MESSAGE = "name must not be blank";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = 5083613650640201101L;

	public InvalidProductNameException() {
		super("name must not be blank");
	}
}
