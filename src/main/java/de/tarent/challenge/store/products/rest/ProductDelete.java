package de.tarent.challenge.store.products.rest;

import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;

/**
 * DELETE Methods for Product
 * calls the product service
 * @author Jan-Henning Schmitz
 *
 */
public class ProductDelete {

	private final ProductService productService;
	private final ProductGet productGet;

	public ProductDelete(ProductService productService, ProductGet productGet) {
		this.productService = productService;
		this.productGet = productGet;
	}

	/**
	 * Deleting a Product by SKU
	 */
	public void deleteBySKU(String sku) {

		// Validate and throw Error if not there
		Product product = productGet.getBySku(sku);

		try {
			this.productService.delete(product);
		} catch (IllegalArgumentException iae) {
			throw new SkuNotFoundException(sku);
		}

	}

	/**
	 * Deleting all Products, JUST FOR TESTING
	 */
	public void deleteAll() {
		this.productService.deleteAll();

	}
}
