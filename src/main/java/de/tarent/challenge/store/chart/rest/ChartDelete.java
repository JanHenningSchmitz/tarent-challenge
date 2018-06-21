package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.store.chart.ChartService;

public class ChartDelete {

	private final ChartService chartService;

	public ChartDelete(ChartService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Deleting all Products, JUST FOR TESTING
	 */
	public void deleteAll() {
		this.chartService.deleteAll();

	}
}