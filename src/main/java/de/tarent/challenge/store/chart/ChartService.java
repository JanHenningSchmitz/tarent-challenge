package de.tarent.challenge.store.chart;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChartService {
	private final ChartCatalog chartCatalog;

	public ChartService(ChartCatalog chartCatalog) {
		this.chartCatalog = chartCatalog;
	}
	
	public Chart createNewChart(Chart chart) {
		return chartCatalog.saveAndFlush(chart);
	}

	public List<Chart> retrieveAllCharts() {
		return chartCatalog.findAll();
	}

	public Optional<Chart> retrieveChartByName(String name) {
		return chartCatalog.findByName(name);
	}

	public Chart addItems(Chart chart) {
		return chartCatalog.saveAndFlush(chart);
	}
}
