package de.tarent.challenge.store.products.rest.validation;

import de.tarent.challenge.exeptions.EanIsEmptyException;
import de.tarent.challenge.exeptions.InvalidProductNameException;
import de.tarent.challenge.exeptions.InvalidSkuException;
import de.tarent.challenge.exeptions.NoEansException;
import de.tarent.challenge.exeptions.PriceLowerZeroException;
import de.tarent.challenge.exeptions.ProductAllreadyInUseException;
import de.tarent.challenge.exeptions.SkuNotFoundException;
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
		// Name: required, not empty
		if (input.getName() == null || input.getName().trim().length() == 0) {
			throw new InvalidProductNameException();
		}
	}

	/**
	 * #Task 2 Add validation for the new attribute: required, greater than 0
	 * 
	 * @param input
	 */
	public void validatePriceData(Product input) {
		// Name: required, greater than 0
		if (input.getPrice() <= 0) {
			throw new PriceLowerZeroException();
		}

	}

	/**
	 * EANs: At least one, non-empty item
	 * 
	 * @param input
	 */
	public void validateEanData(Product input) {
		// Name: required, not empty
		if (input.getEans() == null || input.getEans().size() < 1) {
			throw new NoEansException();
		}

		// Name: not empty
		for (String ean : input.getEans()) {
			if (ean == null || ean.trim().length() == 0)
				throw new EanIsEmptyException();
		}
	}

	/**
	 * SKU: required, not empty, unique
	 */
	public void validateSkuData(Product input) {
		// SKU: required, not empty
		if (input.getSku() == null || input.getSku().trim().length() == 0) {
			throw new InvalidSkuException();
		}

		// SKU: unique
		try {
			if (productGet.getBySku(input.getSku()) != null) {
				throw new ProductAllreadyInUseException(input.getSku());
			}
		} catch (SkuNotFoundException snfe) {
			// Do nothing, since this exception is wanted in this case
		}
	}
}
