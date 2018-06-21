package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.ChartIsEmptyOnCreateException;
import de.tarent.challenge.exeptions.ChartIsNullException;
import de.tarent.challenge.exeptions.ChartNameInvalidException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.item.Chartitem;

public class ChartValidator {

	private final ChartitemValidator chartitemValidator;

	public ChartValidator(ChartitemValidator chartitemValidator) {
		this.chartitemValidator = chartitemValidator;
	}

	public void validateChart(Chart chart) {

		// Validate chart
		if (chart == null) {
			throw new ChartIsNullException();
		}

		String name = chart.getName();
		// Validate and throw Error if not there
		if (name == null || name.trim().length() <= 0) {
			throw new ChartNameInvalidException();
		}

		// Should be at least one item in the chart
		if (chart.getChartitems().size() <= 0) {
			throw new ChartIsEmptyOnCreateException(chart);
		}

		// Validate Items
		for (Chartitem chartitem : chart.getChartitems()) {
			chartitemValidator.validateChartitem(chartitem);
		}
	}
}
