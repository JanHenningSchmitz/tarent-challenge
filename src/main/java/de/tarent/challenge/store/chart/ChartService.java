package de.tarent.challenge.store.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Service class for the Product chart, calls the catalog
 * @author Jan-Henning Schmitz
 *
 */
@Service
public class ChartService {
	private final ChartCatalog chartCatalog;

	public ChartService(ChartCatalog chartCatalog) {
		this.chartCatalog = chartCatalog;
	}

	public Chart createNewChart(Chart chart) {
		return chartCatalog.saveAndFlush(chart);
	}

	public List<Chart> retrieveAllCharts(boolean checkedout) {
		if(!checkedout) {
			return chartCatalog.findAll();
		}else {
			List<Chart> result = new ArrayList<Chart>();
			List<Chart> charts = chartCatalog.findAll();
			
			for(Chart chart : charts) {
				if(chart.isCheckedout()) {
					result.add(chart);
				}
			}
			
			return result;
		}
	}

	public Optional<Chart> retrieveChartByName(String name) {
		return chartCatalog.findByName(name);
	}

	public Chart changeItems(Chart chart) {
		return chartCatalog.saveAndFlush(chart);
	}

	public Chart checkout(Chart chart) {
		return chartCatalog.saveAndFlush(chart);
	}

	public void deleteChart(Chart chart) {
		chartCatalog.delete(chart);
		chartCatalog.flush();
	}
	
	public void deleteAll() {
		chartCatalog.deleteAll();
		chartCatalog.flush();
	}
}
