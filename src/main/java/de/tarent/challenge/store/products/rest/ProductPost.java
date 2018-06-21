package de.tarent.challenge.store.products.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;
import de.tarent.challenge.store.products.rest.validation.ProductValidator;

public class ProductPost {

	private final ProductService productService;
	private final ProductValidator productValidator;

	public ProductPost(ProductService productService, ProductValidator productValidator) {
		this.productValidator = productValidator;
		this.productService = productService;
	}

	/**
	 * ## Task 1: Manage products - Add the ability to create and update products. -
	 * Validate the product and enforce data integrity constraints: - SKU: required,
	 * not empty, unique - Name: required, not empty - EANs: At least one, non-empty
	 * item
	 * 
	 * @param input
	 * @return
	 */
	public ResponseEntity<?> addProduct(Product input) {

		productValidator.validateSkuData(input);
		productValidator.validateNameData(input);
		productValidator.validateEanData(input);
		productValidator.validatePriceData(input);

		Product result = this.productService.save(input);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/products/{id}")
				.buildAndExpand(result.getSku()).toUri();

		return ResponseEntity.created(location).build();

	}
}
