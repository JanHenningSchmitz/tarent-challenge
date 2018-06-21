package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.ErrorWhileAddingItem;
import de.tarent.challenge.exeptions.ErrorWhileDeletingChart;
import de.tarent.challenge.exeptions.ItemNotInChartException;
import de.tarent.challenge.exeptions.NotEnoughItemsInChartEcxeption;
import de.tarent.challenge.exeptions.QuantityBelowZeroException;
import de.tarent.challenge.exeptions.SkuNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductService;

public class ChartPut {

	private final ProductService productService;
	private final ChartService chartService;
	private final ChartGet chartGet;

	public ChartPut(ChartService chartService, ChartGet chartGet, ProductService productService) {
		this.chartService = chartService;
		this.chartGet = chartGet;
		this.productService = productService;
	}

	/**
	 * Adding a Item in a Quantity to the Chart and altering the total price
	 */
	public Chart addItem(String name, String sku, int quantity) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);
		// Validate the quantity
		quantityValidation(quantity);
		// Validate the Product
		Product product = getProduct(sku);
		
		boolean found = false;
		// should be not null at this point
		for (Chartitem chartitem : chart.getChartitems()) {
			if (chartitem.getSku().equals(sku)) {
				chartitem.addQuantity(quantity);
				found = true;
				break;
			}
		}
		
		// If not found, add a new Item to the Chart
		if (!found) {
			chart.getChartitems().add(new Chartitem(chart, sku, quantity));
		}
		
		// Altering the total price
		double totalprice = chart.getTotalprice() + (product.getPrice() * quantity);
		chart.setTotalprice(totalprice);

		try {
			return this.chartService.changeItems(chart);
		} catch (Exception e) {
			System.out.println(e);
			throw new ErrorWhileAddingItem(name, product.getSku(), quantity);
		}

	}
	
	/**
	 * Deleting a Item in a Quantity from the Chart and altering the total price
	 * Returns Chart if OK, returns null if chart is deleted
	 */
	public Chart deleteItem(String name, String sku, int quantity) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);
		// Validate the quantity
		quantityValidation(quantity);
		// Validate the Product
		Product product = getProduct(sku);
		
		Chartitem forRemove = null;
		// should be not null at this point
		for (Chartitem chartitem : chart.getChartitems()) {
			if (chartitem.getSku().equals(sku)) {
				
				// Can't delete more than in chart
				if(quantity > chartitem.getQuantity()) {
					throw new NotEnoughItemsInChartEcxeption(name, quantity);
				}
				
				chartitem.subQuantity(quantity);
				forRemove = chartitem;
				break;
			}
		}
		
		// Can't delete if it isn't in the chart
		if (forRemove == null) {
			throw new ItemNotInChartException(name, sku, quantity);
		}

		// Alter the total price
		double totalprice = chart.getTotalprice() + (product.getPrice() * quantity);
		chart.setTotalprice(totalprice);
		
		// Remove from Chart if there are non left
		if(forRemove.getQuantity() <= 0) {
			chart.getChartitems().remove(forRemove);
		}

		// Delete the Chart if there are no Items left
		if(chart.getChartitems().size() <= 0) {
			
			// Change the DB
			try {
				this.chartService.deleteChart(chart);
				return null;
			} catch (Exception e) {
				System.out.println(e);
				throw new ErrorWhileDeletingChart(name);
			}
			
		}else {
		// Alter the Chart if there are Items left
			
			// Change the DB
			try {
				return this.chartService.changeItems(chart);
			} catch (Exception e) {
				System.out.println(e);
				throw new ErrorWhileAddingItem(name, product.getSku(), quantity);
			}
			
		}

	}
	
	
	
	private void quantityValidation(int quantity) {
		if(quantity <= 0) {
			throw new QuantityBelowZeroException(quantity);
		}
	}
	
	private Product getProduct(String sku) {
		return this.productService.retrieveProductBySku(sku).orElseThrow(() -> new SkuNotFoundException(sku));
	}

}
