package de.tarent.challenge.store.chart.rest.validation;

import de.tarent.challenge.exeptions.ChartAllreadyInUseException;
import de.tarent.challenge.exeptions.ChartIsCheckedOutException;
import de.tarent.challenge.exeptions.ChartIsEmptyOnCreateException;
import de.tarent.challenge.exeptions.ChartIsNullException;
import de.tarent.challenge.exeptions.ChartNameInvalidException;
import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.exeptions.ChartWithWrongTotalPriceException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.chart.rest.ChartGet;
import de.tarent.challenge.store.products.Product;

public class ChartValidator {

	private final ChartitemValidator chartitemValidator;
	private final ChartGet chartGet;

	public ChartValidator(ChartGet chartGet, ChartitemValidator chartitemValidator) {
		this.chartGet = chartGet;
		this.chartitemValidator = chartitemValidator;
	}

	public Chart validateChartForAltering(String name) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);

		// Is Chart open for altering?
		if (chart.isCheckedout()) {
			throw new ChartIsCheckedOutException(chart);
		}

		return chart;
	}

	public void validateNewChart(Chart chart) {

		// Validate chart
		if (chart == null) {
			throw new ChartIsNullException();
		}

		String name = chart.getName();
		// Validate and throw Error if empty or null
		if (name == null || name.trim().length() <= 0) {
			throw new ChartNameInvalidException();
		}

		// Validate and throw Error if allready there
		try {
			if (chartGet.retrieveChartByName(chart.getName()) != null) {
				throw new ChartAllreadyInUseException(chart.getName());
			}
		} catch (ChartNotFoundException cnfe) {
			// Do nothing, since this exception is wanted in this case
		}

		// Should be at least one item in the chart
		if (chart.getItems().size() <= 0) {
			throw new ChartIsEmptyOnCreateException(chart);
		}

		double expectedTotalPrice = 0;

		// Validate Items
		for (String chartitem : chart.getItems()) {
			Product product = chartitemValidator.validateChartitem(chartitem);
			// Check if available
			chartitemValidator.productAvailableForAdding(product);
			expectedTotalPrice += product.getPrice() * Chartitem.getQuantity(chartitem);
		}

		// Is the totalprice correct?
		if (chart.getTotalprice() != expectedTotalPrice) {
			throw new ChartWithWrongTotalPriceException(chart, expectedTotalPrice);
		}
	}
}
