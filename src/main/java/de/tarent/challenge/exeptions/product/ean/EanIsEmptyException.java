package de.tarent.challenge.exeptions.product.ean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason=EanIsEmptyException.MESSAGE)
public class EanIsEmptyException extends RuntimeException {

	public static final String MESSAGE = "ean should not be empty";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;
	
	private static final long serialVersionUID = -8887042531049391290L;

	public EanIsEmptyException() {
		super("ean should not be empty");
	}
}