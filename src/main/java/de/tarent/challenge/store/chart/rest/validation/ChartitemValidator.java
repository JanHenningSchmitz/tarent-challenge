package de.tarent.challenge.store.chart.rest.validation;

import de.tarent.challenge.exeptions.chart.ChartItemIsNullException;
import de.tarent.challenge.exeptions.chart.ChartitemStringNotValidException;
import de.tarent.challenge.exeptions.chart.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.exeptions.chart.ProductIsNotAvailableForCheckOutException;
import de.tarent.challenge.exeptions.chart.quantity.ChartitemQuantityBelowZeroException;
import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;

public class ChartitemValidator {

	private final ProductService productService;

	public ChartitemValidator(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Validating the given chart item (nut null, quantity > 0, sku in DB)
	 * 
	 * @param chartItem
	 * @return
	 */
	public Product validateChartitem(String chartitem) {

		// Validate ChartItem != null
		if (chartitem == null) {
			throw new ChartItemIsNullException();
		}
		
		if(!Chartitem.isValidString(chartitem)){
			throw new ChartitemStringNotValidException();
		}

		String sku = Chartitem.getSku(chartitem);
		int quantity = Chartitem.getQuantity(chartitem);

		// Validate the quantity
		quantityValidation(sku, quantity);

		// Validate the Product
		return getProduct(sku);
	}

	private void quantityValidation(String sku, int quantity) {
		if (quantity <= 0) {
			throw new ChartitemQuantityBelowZeroException(sku, quantity);
		}
	}

	private Product getProduct(String sku) {
		return this.productService.retrieveProductBySku(sku).orElseThrow(() -> new SkuNotFoundException(sku));
	}

	/**
	 * Check if Product is available
	 */
	public void productAvailableForAdding(Product product) {
		if (!product.isAvailable()) {
			throw new ProductIsNotAvailableForAddingException(product);
		}
	}

	/**
	 * Check if Product is available
	 */
	public void productAvailableForCheckout(String chartitem) {

		String sku = Chartitem.getSku(chartitem);
		
		Product product = getProduct(sku);

		if (!product.isAvailable()) {
			throw new ProductIsNotAvailableForCheckOutException(product);
		}
	}
}
