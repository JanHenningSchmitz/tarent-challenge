package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;

public class ChartGet {

	private final ChartService chartService;

	public ChartGet(ChartService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Get All Charts
	 * 
	 * @return
	 */
	public Iterable<Chart> retrieveCharts() {
		return chartService.retrieveAllCharts();
	}

	/**
	 * Get one Chart by ID
	 * 
	 * @param sku
	 * @return
	 */
	public Chart retrieveChartByName(String name) {
		return chartService.retrieveChartByName(name).orElseThrow(() -> new ChartNotFoundException(name));
	}

}
