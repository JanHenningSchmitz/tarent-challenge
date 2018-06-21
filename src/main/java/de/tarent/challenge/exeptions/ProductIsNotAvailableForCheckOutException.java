package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.products.Product;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductIsNotAvailableForCheckOutException extends RuntimeException {

	private static final long serialVersionUID = -5042585561613512478L;

	public ProductIsNotAvailableForCheckOutException(Product product) {
		super("product '" + product.getSku() + "' is not available, your chart can't be checked out.");
	}
}
