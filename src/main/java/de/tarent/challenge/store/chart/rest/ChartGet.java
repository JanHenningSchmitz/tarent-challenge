package de.tarent.challenge.store.chart.rest;

import de.tarent.challenge.exeptions.chart.ChartNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;

/**
 * Get Methods for Chart
 * calls the chart service
 * @author Jan-Henning Schmitz
 *
 */
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
	public Iterable<Chart> retrieveCharts(boolean checkedout) {
				
		return chartService.retrieveAllCharts(checkedout);
	}

	/**
	 * Get one Chart by name
	 * 
	 * @param sku
	 * @return
	 */
	public Chart retrieveChartByName(String name) {
		return chartService.retrieveChartByName(name).orElseThrow(() -> new ChartNotFoundException(name));
	}

}
