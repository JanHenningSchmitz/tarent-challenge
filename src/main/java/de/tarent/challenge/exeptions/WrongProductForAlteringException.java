package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.products.Product;

@Deprecated
@ResponseStatus(value = HttpStatus.CONFLICT, reason = WrongProductForAlteringException.MESSAGE)
public class WrongProductForAlteringException extends RuntimeException {

	private static final long serialVersionUID = 3332525975775316922L;

	public static final String MESSAGE = "altering with wrong product";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public WrongProductForAlteringException(String sku, Product product) {
		super("Tried to alter '" + sku + "' with data for '" + product.getSku() + "'.");
	}
}