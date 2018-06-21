package de.tarent.challenge.store.chart.rest.validation;

import de.tarent.challenge.exeptions.ChartIsCheckedOutException;
import de.tarent.challenge.exeptions.ChartIsEmptyOnCreateException;
import de.tarent.challenge.exeptions.ChartIsNullException;
import de.tarent.challenge.exeptions.ChartNameInvalidException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.chart.rest.ChartGet;

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
