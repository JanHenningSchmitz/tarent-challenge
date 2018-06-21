package de.tarent.challenge.store.chart.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;
import de.tarent.challenge.store.chart.rest.validation.ChartValidator;

public class ChartPost {

	private final ChartService chartService;
	private final ChartValidator chartValidator;

	public ChartPost(ChartService chartService, ChartValidator chartValidator) {
		this.chartService = chartService;
		this.chartValidator = chartValidator;
	}

	/**
	 * Creating a new Chart with the given name
	 * 
	 * @param name
	 * @return
	 */
	public ResponseEntity<?> createNewChart(Chart chart) {

		chartValidator.validateNewChart(chart);

		Chart result = this.chartService.createNewChart(chart);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/charts/{name}").buildAndExpand(result)
				.toUri();

		return ResponseEntity.created(location).build();

	}
}
