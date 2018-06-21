package de.tarent.challenge.store.products.rest.validation;

import de.tarent.challenge.exeptions.EanIsEmptyException;
import de.tarent.challenge.exeptions.NoEansException;
import de.tarent.challenge.exeptions.SkuNotFoundException;
import de.tarent.challenge.exeptions.product.name.InvalidProductNameException;
import de.tarent.challenge.exeptions.product.price.PriceLowerZeroException;
import de.tarent.challenge.exeptions.product.sku.InvalidSkuException;
import de.tarent.challenge.exeptions.product.sku.ProductAllreadyInUseException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.rest.ProductGet;

public class ProductValidator {

	private final ProductGet productGet;

	public ProductValidator(ProductGet productGet) {
		this.productGet = productGet;
	}

	/**
	 * Name: required, not empty
	 * 
	 * @param input
	 */
	public void validateNameData(Product input) {
		validateNameData(input.getName());
	}

	/**
	 * Name: required, not empty
	 * 
	 * @param input
	 */
	public void validateNameData(String name) {
		// Name: required, not empty
		if (name == null || name.trim().length() == 0) {
			throw new InvalidProductNameException();
		}
	}

	/**
	 * #Task 2 Add validation for the new attribute: required, greater than 0
	 * 
	 * @param input
	 */
	public void validatePriceData(Product input) {
		validatePriceData(input.getPrice());
	}

	public void validatePriceData(double price) {
		// Name: required, greater than 0
		if (price <= 0) {
			throw new PriceLowerZeroException();
		}

	}

	/**
	 * EANs: At least one, non-empty item
	 * 
	 * @param input
	 */
	public void validateEanData(Product input) {
		if (input.getEans() == null || input.getEans().size() < 1) {
			throw new NoEansException();
		}

		for (String ean : input.getEans()) {
			validateEan(ean);
		}
	}

	/**
	 * EANs: At least one, non-empty item
	 * 
	 * @param input
	 */
	public void validateEan(String ean) {
		if (ean == null || ean.trim().length() == 0)
			throw new EanIsEmptyException();
	}

	/**
	 * SKU: required, not empty, unique
	 * 
	 * @throws Exception
	 */
	public Product validateSkuData(Product input, boolean newProduct) throws Exception {
		return validateSkuData(input.getSku(), newProduct);
	}

	/**
	 * SKU: required, not empty, unique
	 */
	public Product validateSkuData(String sku, boolean newProduct) throws Exception {
		// SKU: required, not empty
		if (sku == null || sku.trim().length() == 0) {
			throw new InvalidSkuException();
		}

		if (newProduct) {

			// SKU: unique
			try {
				if (productGet.getBySku(sku) != null) {
					throw new ProductAllreadyInUseException(sku);
				}
			} catch (SkuNotFoundException snfe) {
				// Do nothing, since this exception is wanted in this case
				return null;
			}
		}

		// Throws a Exception if not there
		return productGet.getBySku(sku);

	}
}
