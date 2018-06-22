package de.tarent.challenge.store.products.rest;

import de.tarent.challenge.exeptions.product.ErrorWhileChangingException;
import de.tarent.challenge.exeptions.product.ean.CantDeleteLastEan;
import de.tarent.challenge.exeptions.product.ean.EanAllreadyOnProductException;
import de.tarent.challenge.exeptions.product.ean.EanNotOnProductException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;
import de.tarent.challenge.store.products.rest.validation.ProductValidator;

/**
 * PUT Methods for Product
 * calls the product service
 * @author Jan-Henning Schmitz
 *
 */
public class ProductPut {

	private final ProductService productService;
	private final ProductValidator productValidator;

	public ProductPut(ProductService productService, ProductValidator productValidator) {
		this.productValidator = productValidator;
		this.productService = productService;
	}

	/**
	 * Changing the Name of a given Product
	 * 
	 * @param sku
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public Product changeProductName(String sku, String newName) throws Exception {

		// Validate and throw Error if not there
		productValidator.validateNameData(newName);

		Product product = productValidator.validateSkuData(sku, false);
		product.setName(newName);

		try {
			return this.productService.change(product);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}

	/**
	 * Changing the price of a given Product
	 * 
	 * @param sku
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public Product changeProductPrice(String sku, double newPrice) throws Exception {

		// Validate and throw Error if not there
		productValidator.validatePriceData(newPrice);

		Product product = productValidator.validateSkuData(sku, false);
		product.setPrice(newPrice);

		try {
			return this.productService.change(product);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}

	/**
	 * Changing the available flag of a given Product
	 * 
	 * @param sku
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public Product changeProductAvailable(String sku, boolean newAvailable) throws Exception {

		// Validate and throw Error if not there
		Product product = productValidator.validateSkuData(sku, false);
		product.setAvailable(newAvailable);

		try {
			return this.productService.change(product);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}

	/**
	 * Removing a EAN from the Product
	 * 
	 * @param sku
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public Product removeEanFromProduct(String sku, String ean) throws Exception {

		productValidator.validateEan(ean);

		// Validate and throw Error if not there
		Product product = productValidator.validateSkuData(sku, false);

		if (!product.getEans().contains(ean)) {
			throw new EanNotOnProductException();
		}

		product.getEans().remove(ean);

		if (product.getEans().size() <= 0) {
			throw new CantDeleteLastEan();
		}

		try {
			return this.productService.change(product);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}

	/**
	 * adding a EAN to the Product
	 * 
	 * @param sku
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public Product addEanToProduct(String sku, String ean) throws Exception {

		productValidator.validateEan(ean);

		// Validate and throw Error if not there
		Product product = productValidator.validateSkuData(sku, false);

		if (product.getEans().contains(ean)) {
			throw new EanAllreadyOnProductException();
		}

		product.getEans().add(ean);

		try {
			return this.productService.change(product);
		} catch (Exception e) {
			throw new ErrorWhileChangingException(sku);
		}

	}
}
