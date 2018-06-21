package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.products.Product;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductIsNotAvailableForAddingException extends RuntimeException {

	private static final long serialVersionUID = 2844342099470370687L;

	public ProductIsNotAvailableForAddingException(Product product) {
		super("product '" + product.getSku() + "' is not available, it can't be added to the chart.");
	}
}
