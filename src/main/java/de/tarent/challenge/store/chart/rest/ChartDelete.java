package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.chart.ChartNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;

/**
 * DELETE Methods for Chart
 * calls the chart service
 * @author Jan-Henning Schmitz
 *
 */
public class ChartDelete {

	private final ChartService chartService;
	private final ChartGet chartGet;

	public ChartDelete(ChartService chartService, ChartGet chartGet) {
		this.chartService = chartService;
		this.chartGet = chartGet;
	}

	/**
	 * Deleting all Products, JUST FOR TESTING
	 */
	public void deleteAll() {
		this.chartService.deleteAll();

	}

	/**
	 * Deleting a Chart by a name
	 */
	public void deleteChartByName(String name) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);

		try {
			this.chartService.deleteChart(chart);
		} catch (IllegalArgumentException iae) {
			throw new ChartNotFoundException(name);
		}

	}
}