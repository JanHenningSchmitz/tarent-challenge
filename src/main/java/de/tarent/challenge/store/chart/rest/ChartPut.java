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
	public Chart addItem(String name, Chartitem chartitem) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

		// Validate Product and throw Error if not there
		Product product = chartitemValidator.validateChartitem(chartitem);

		boolean found = false;
		// should be not null at this point
		for (Chartitem itemFromChart : chart.getChartitems()) {
			if (itemFromChart.getSku().equals(chartitem.getSku())) {
				chartitem.addQuantity(chartitem.getQuantity());
				found = true;
				break;
			}
		}

		// If not found, add a new Item to the Chart
		if (!found) {
			chart.getChartitems().add(new Chartitem(chartitem.getSku(), chartitem.getQuantity()));
		}

		// Altering the total price
		double totalprice = chart.getTotalprice() + (product.getPrice() * chartitem.getQuantity());
		chart.setTotalprice(totalprice);

		try {
			return this.chartService.changeItems(chart);
		} catch (Exception e) {
			System.out.println(e);
			throw new ErrorWhileAddingItem(name, product.getSku(), chartitem.getQuantity());
		}

	}

	/**
	 * Deleting a Item in a Quantity from the Chart and altering the total price
	 * Returns Chart if OK, returns null if chart is deleted
	 */
	public Chart deleteItem(String name, Chartitem chartitem) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

		// Validate Product and throw Error if not there
		Product product = chartitemValidator.validateChartitem(chartitem);

		Chartitem forRemove = null;
		// should be not null at this point
		for (Chartitem itemFromChart : chart.getChartitems()) {
			if (itemFromChart.getSku().equals(chartitem.getSku())) {

				// Can't delete more than in chart
				if (chartitem.getQuantity() > chartitem.getQuantity()) {
					throw new NotEnoughItemsInChartEcxeption(name, chartitem.getQuantity());
				}

				chartitem.subQuantity(chartitem.getQuantity());
				forRemove = chartitem;
				break;
			}
		}

		// Can't delete if it isn't in the chart
		if (forRemove == null) {
			throw new ItemNotInChartException(name, chartitem.getSku(), chartitem.getQuantity());
		}

		// Alter the total price
		double totalprice = chart.getTotalprice() + (product.getPrice() * chartitem.getQuantity());
		chart.setTotalprice(totalprice);

		// Remove from Chart if there are non left
		if (forRemove.getQuantity() <= 0) {
			chart.getChartitems().remove(forRemove);
		}

		// Delete the Chart if there are no Items left
		if (chart.getChartitems().size() <= 0) {

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
				throw new ErrorWhileAddingItem(name, product.getSku(), chartitem.getQuantity());
			}

		}

	}
	
	/**
	 * Checking out the chart and closing for further altering
	 * @param name
	 */
	public Chart checkOutChart(String name) {

		// Validate Chart and throw Error if not there
		Chart chart = chartValidator.validateChartForAltering(name);

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
