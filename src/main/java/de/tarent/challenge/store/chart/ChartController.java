package de.tarent.challenge.store.chart;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.exeptions.ErrorWhileAddingItem;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RestController
@RequestMapping("/charts")
public class ChartController {
	private final ChartService chartService;

	public ChartController(ChartService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Get All Charts
	 * 
	 * @return
	 */
	@GetMapping
	public Iterable<Chart> retrieveCharts() {
		return chartService.retrieveAllCharts();
	}

	/**
	 * Get one Chart by ID
	 * 
	 * @param sku
	 * @return
	 */
	@GetMapping("/{name}")
	public Chart retrieveChartByName(@PathVariable String name) {
		return checkIfIdIsInDB(name);
	}

	@PostMapping
	public ResponseEntity<?> createNewChart(@RequestBody String name) {

		// Validate and throw Error if not there
		Chart chart = new Chart(name);

		Chart result = this.chartService.createNewChart(chart);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/charts/{name}").buildAndExpand(result).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{name}/add")
	public Chart addItem(@PathVariable String name, @RequestBody Chartitem item) {

		System.out.println("Are you here?");

		// Validate and throw Error if not there
		Chart chart = this.checkIfIdIsInDB(name);

		boolean found = false;
		// should be not null at this point
		for (Chartitem chartitem : chart.getChartitems()) {
			if (chartitem.getSku().equals(item.getSku())) {
				chartitem.addQuantity(item.getQuantity());
				found = true;
			}
		}
		if (!found) {
			chart.getChartitems().add(item);
		}

		try {
			return this.chartService.addItems(chart);
		} catch (Exception e) {
			throw new ErrorWhileAddingItem(name, item.getSku(), item.getQuantity());
		}

	}

	/**
	 * Validation the SKU, needs to be before altering and deleting
	 * 
	 * @param sku
	 */
	private Chart checkIfIdIsInDB(String name) {
		return chartService.retrieveChartByName(name).orElseThrow(() -> new ChartNotFoundException(name));
	}

}
