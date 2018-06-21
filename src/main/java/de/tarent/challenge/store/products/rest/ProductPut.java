package de.tarent.challenge.store.products.rest;

import de.tarent.challenge.exeptions.ErrorWhileChangingException;
import de.tarent.challenge.exeptions.WrongProductForAlteringException;
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

		if(!sku.equals(input.getSku())) {
			throw new WrongProductForAlteringException(sku, input);
		}
		
		// Validate and throw Error if not there
		productValidator.validateSkuData(input, false);
		productValidator.validateEanData(input);
		productValidator.validatePriceData(input);
		productValidator.validateNameData(input);

		try {
			return this.productService.change(input);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}
}
