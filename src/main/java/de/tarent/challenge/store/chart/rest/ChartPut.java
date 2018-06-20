package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.ErrorWhileAddingItem;
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

	public Chart addItem(String name, String sku, int quantity) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);

		Product product = getProduct(sku);
		// TODO ERROR HANDLING
		
		boolean found = false;
		// should be not null at this point
		for (Chartitem chartitem : chart.getChartitems()) {
			if (chartitem.getSku().equals(sku)) {
				chartitem.addQuantity(quantity);
				found = true;
			}
		}
		if (!found) {
			chart.getChartitems().add(new Chartitem(sku, quantity));
		}
		
		double totalprice = chart.getTotalprice() + (product.getPrice() * quantity);
		chart.setTotalprice(totalprice);

		try {
			return this.chartService.addItems(chart);
		} catch (Exception e) {
			System.out.println(e);
			throw new ErrorWhileAddingItem(name, product.getSku(), quantity);
		}

	}
	
	private Product getProduct(String sku) {
		return this.productService.retrieveProductBySku(sku).orElseThrow(() -> new SkuNotFoundException(sku));
	}

}
