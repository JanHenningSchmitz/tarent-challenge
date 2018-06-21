package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.products.Product;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ProductIsNotAvailableForCheckOutException.MESSAGE)
public class ProductIsNotAvailableForCheckOutException extends RuntimeException {

	public static final String MESSAGE = "product is not available, your chart can't be checked out.";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -5042585561613512478L;

	public ProductIsNotAvailableForCheckOutException(Product product) {
		super("product '" + product.getSku() + "' is not available, your chart can't be checked out.");
	}
}
