package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;

public class ChartDelete {

	private final ChartService chartService;
	private final ChartGet chartGet;

	public ChartDelete(ChartService chartService, ChartGet chartGet) {
		this.chartService = chartService;
		this.chartGet = chartGet;
	}

	public void deleteItem(String name, String sku, int quantity) {

		// Validate and throw Error if not there
		Chart chart = chartGet.retrieveChartByName(name);

		// TODO FUNCTIONS

		chartService.changeItems(chart);
	}
}