package de.tarent.challenge.store.chart.rest.validation;

import de.tarent.challenge.exeptions.ChartItemIsNullException;
import de.tarent.challenge.exeptions.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.exeptions.QuantityBelowZeroException;
import de.tarent.challenge.exeptions.SkuNotFoundException;
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
	public Product validateChartitem(Chartitem chartItem) {

		// Validate ChartItem != null
		if (chartItem == null) {
			throw new ChartItemIsNullException();
		}
		// Validate the quantity
		quantityValidation(chartItem.getQuantity());
		
		// Validate the Product
		return getProduct(chartItem.getSku());
	}

	private void quantityValidation(int quantity) {
		if (quantity <= 0) {
			throw new QuantityBelowZeroException(quantity);
		}
	}

	private Product getProduct(String sku) {
		return this.productService.retrieveProductBySku(sku).orElseThrow(() -> new SkuNotFoundException(sku));
	}
	
	
	/**
	 * Check if Product is available
	 */
	public void productAvailableForAdding(Product product) {
		if(!product.isAvailable()) {
			throw new ProductIsNotAvailableForAddingException(product);
		}
	}
	
	/**
	 * Check if Product is available
	 */
	public void productAvailableForCheckout(Chartitem chartitem) {
		
		Product product = getProduct(chartitem.getSku());
		
		if(!product.isAvailable()) {
			throw new ProductIsNotAvailableForAddingException(product);
		}
	}
}
