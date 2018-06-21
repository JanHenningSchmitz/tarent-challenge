package de.tarent.challenge.exeptions.product.price;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = PriceLowerZeroException.MESSAGE)
public class PriceLowerZeroException extends RuntimeException {

	public static final String MESSAGE = "price must be greater than 0";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = 8017031354449124427L;

	public PriceLowerZeroException() {
		super("price must be greater than 0");
	}
}