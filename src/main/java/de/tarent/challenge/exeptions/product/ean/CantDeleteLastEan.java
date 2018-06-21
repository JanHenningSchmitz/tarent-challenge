package de.tarent.challenge.exeptions.product.ean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = CantDeleteLastEan.MESSAGE)
public class CantDeleteLastEan extends RuntimeException {

	private static final long serialVersionUID = -7916576125328755048L;

	public static final String MESSAGE = "cant delete last ean";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public CantDeleteLastEan() {
		super(MESSAGE);
	}
}
