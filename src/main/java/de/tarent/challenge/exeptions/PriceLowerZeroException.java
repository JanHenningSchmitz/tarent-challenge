package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PriceLowerZeroException extends RuntimeException {

	private static final long serialVersionUID = 8017031354449124427L;

	public PriceLowerZeroException() {
		super("price must be greater than 0");
	}
}