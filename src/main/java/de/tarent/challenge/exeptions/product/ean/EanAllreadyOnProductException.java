package de.tarent.challenge.exeptions.product.ean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = EanAllreadyOnProductException.MESSAGE)
public class EanAllreadyOnProductException extends RuntimeException {

	private static final long serialVersionUID = 7497801050707886747L;
	public static final String MESSAGE = "ean allready on product";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public EanAllreadyOnProductException() {
		super(MESSAGE);
	}
}
