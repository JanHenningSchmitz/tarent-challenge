package de.tarent.challenge.store.products.rest;

import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;

public class ProductGet {

	private final ProductService productService;

	public ProductGet(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Get All Products
	 * 
	 * @return
	 */
	public Iterable<Product> getAll() {
		return productService.retrieveAllProducts();
	}
	
	public Product getBySku(String sku) {

		// Validate and throw Error if not there
		return this.checkIfSkuIsInDB(sku);
	}
	
	/**
	 * Validation the SKU, needs to be before altering and deleting
	 * 
	 * @param sku
	 */
	private Product checkIfSkuIsInDB(String sku) {
		return productService.retrieveProductBySku(sku).orElseThrow(() -> new SkuNotFoundException(sku));
	}
}
