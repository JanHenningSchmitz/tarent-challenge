package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.products.Product;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ProductIsNotAvailableForAddingException.MESSAGE)
public class ProductIsNotAvailableForAddingException extends RuntimeException {

	public static final String MESSAGE = "product is not available, it can't be added to the chart.";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = 2844342099470370687L;

	public ProductIsNotAvailableForAddingException(Product product) {
		super("product '" + product.getSku() + "' is not available, it can't be added to the chart.");
	}
}
