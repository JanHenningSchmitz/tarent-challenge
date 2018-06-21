package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.CouldNotCheckOutChartException;
import de.tarent.challenge.exeptions.ErrorWhileAddingItem;
import de.tarent.challenge.exeptions.ErrorWhileDeletingChart;
import de.tarent.challenge.exeptions.ItemNotInChartException;
import de.tarent.challenge.exeptions.NotEnoughItemsInChartEcxeption;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.chart.rest.validation.ChartValidator;
import de.tarent.challenge.store.chart.rest.validation.ChartitemValidator;
import de.tarent.challenge.store.products.Product;

public class ChartPut {

	private final ChartValidator chartValidator;
	private final ChartitemValidator chartitemValidator;
	private final ChartService chartService;

	public ChartPut(ChartService chartService, ChartValidator chartValidator, ChartitemValidator chartitemValidator) {
		this.chartService = chartService;
		this.chartitemValidator = chartitemValidator;
		this.chartValidator = chartValidator;
	}

	/**
	 * Adding a Item in a Quantity to the Chart and altering the total price
	 */
	public Chart addItem(String name, String chartitem) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

		String sku = Chartitem.getSku(chartitem);
		int quantity = Chartitem.getQuantity(chartitem);

		// Validate Product and throw Error if not there
		Product product = chartitemValidator.validateChartitem(chartitem);

		// Check if Item is available
		chartitemValidator.productAvailableForAdding(product);

		boolean found = false;
		// should be not null at this point
		for (String itemFromChart : chart.getItems()) {
			if (Chartitem.getSku(itemFromChart).equals(sku)) {

				chartitem = Chartitem.addQuantity(itemFromChart, quantity);
				found = true;
				break;
			}
		}

		// If not found, add a new Item to the Chart
		if (!found) {
			chart.getItems().add(Chartitem.createChartitem(sku, quantity));
		}

		// Altering the total price
		double totalprice = chart.getTotalprice() + (product.getPrice() * quantity);
		chart.setTotalprice(totalprice);

		try {
			return this.chartService.changeItems(chart);
		} catch (Exception e) {
			throw new ErrorWhileAddingItem(name, product.getSku(), quantity);
		}

	}

	/**
	 * Deleting a Item in a Quantity from the Chart and altering the total price
	 * Returns Chart if OK, returns null if chart is deleted
	 */
	public Chart deleteItem(String name, String chartitem) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

		String sku = Chartitem.getSku(chartitem);
		int quantity = Chartitem.getQuantity(chartitem);

		// Validate Product and throw Error if not there
		Product product = chartitemValidator.validateChartitem(chartitem);

		String forRemove = null;
		// should be not null at this point
		for (String itemFromChart : chart.getItems()) {
			if (Chartitem.getSku(itemFromChart).equals(sku)) {

				// Can't delete more than in chart
				if (quantity > Chartitem.getQuantity(itemFromChart)) {
					throw new NotEnoughItemsInChartEcxeption(name, quantity);
				}

				chartitem = Chartitem.subQuantity(itemFromChart, quantity);
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
		if (Chartitem.getQuantity(forRemove) <= 0) {
			chart.getItems().remove(forRemove);
		}

		// Delete the Chart if there are no Items left
		if (chart.getItems().size() <= 0) {

			// Change the DB
			try {
				this.chartService.deleteChart(chart);
				return null;
			} catch (Exception e) {
				System.out.println(e);
				throw new ErrorWhileDeletingChart(name);
			}

		} else {
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

	/**
	 * Checking out the chart and closing for further altering
	 * 
	 * @param name
	 */
	public Chart checkOutChart(String name) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

		// for all items, check if they are there
		for (String chartitem : chart.getItems()) {
			// Check if Item is available
			chartitemValidator.productAvailableForCheckout(chartitem);
		}

		// Set the flag
		chart.setCheckedout();

		// Change the DB
		try {
			return this.chartService.checkout(chart);
		} catch (Exception e) {
			System.out.println(e);
			throw new CouldNotCheckOutChartException(name);
		}

	}

}
