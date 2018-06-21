package de.tarent.challenge.exeptions.product.ean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = EanNotOnProductException.MESSAGE)
public class EanNotOnProductException extends RuntimeException {

	private static final long serialVersionUID = -5775807034371108253L;
	public static final String MESSAGE = "ean not on product";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public EanNotOnProductException() {
		super(MESSAGE);
	}
}
