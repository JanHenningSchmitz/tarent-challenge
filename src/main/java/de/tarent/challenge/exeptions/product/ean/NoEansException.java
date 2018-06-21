package de.tarent.challenge.exeptions.product.ean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason=NoEansException.MESSAGE)
public class NoEansException extends RuntimeException {
	
	public static final String MESSAGE = "there has to be at least on ean";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -5356020281436260106L;

	public NoEansException() {
		super("there has to be at least on ean");
	}
}