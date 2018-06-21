package de.tarent.challenge.store.chart.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tarent.challenge.exeptions.ChartNameInvalidException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartService;

public class ChartPost {

	private final ChartService chartService;

	public ChartPost(ChartService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Creating a new Chart with the given name
	 * @param name
	 * @return
	 */
	public ResponseEntity<?> createNewChart(String name) {

		// TODO: Only if there is at least one Item!
		
		// Validate and throw Error if not there
		if (name == null || name.trim().length() <= 0) {
			throw new ChartNameInvalidException();
		}

		Chart chart = new Chart(name);

		Chart result = this.chartService.createNewChart(chart);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/charts/{name}").buildAndExpand(result)
				.toUri();

		return ResponseEntity.created(location).build();

	}

}
