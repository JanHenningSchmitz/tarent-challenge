package de.tarent.challenge.store.products.rest;

import de.tarent.challenge.exeptions.ErrorWhileChangingException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;
import de.tarent.challenge.store.products.rest.validation.ProductValidator;

public class ProductPut {
	
	private final ProductService productService;
	private final ProductValidator productValidator;

	public ProductPut(ProductService productService, ProductValidator productValidator) {
		this.productValidator = productValidator;
		this.productService = productService;
	}
	
	public Product changeProduct(String sku, Product input) {

		// TODO: What if sku != product sku?
		
		// Validate and throw Error if not there
		productValidator.validateSkuData(input);

		try {
			return this.productService.change(input);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}
}
