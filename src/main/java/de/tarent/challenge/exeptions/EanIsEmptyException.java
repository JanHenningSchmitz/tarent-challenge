package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EanIsEmptyException extends RuntimeException {

	private static final long serialVersionUID = -8887042531049391290L;

	public EanIsEmptyException() {
		super("ean should not be empty");
	}
}