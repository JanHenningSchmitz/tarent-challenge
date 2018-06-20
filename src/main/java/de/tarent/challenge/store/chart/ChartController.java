package de.tarent.challenge.store.chart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.chart.rest.ChartGet;
import de.tarent.challenge.store.chart.rest.ChartPost;
import de.tarent.challenge.store.chart.rest.ChartPut;
import de.tarent.challenge.store.products.ProductService;

@RestController
@RequestMapping("/charts")
public class ChartController {
	private final ChartGet chartGet;
	private final ChartPost chartPost;
	private final ChartPut chartPut;

	public ChartController(ChartService chartService, ProductService productService) {
		this.chartGet = new ChartGet(chartService);
		this.chartPost = new ChartPost(chartService);
		this.chartPut = new ChartPut(chartService, chartGet, productService);
	}

	/**
	 * Get All Charts
	 * 
	 * @return
	 */
	@GetMapping
	public Iterable<Chart> retrieveCharts() {
		return chartGet.retrieveCharts();
	}

	/**
	 * Get one Chart by ID
	 * 
	 * @param sku
	 * @return
	 */
	@GetMapping("/{name}")
	public Chart retrieveChartByName(@PathVariable String name) {
		return chartGet.retrieveChartByName(name);
	}

	/**
	 * Create a new Chart with a specific name
	 * 
	 * @param name
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> createNewChart(@RequestBody String name) {
		return chartPost.createNewChart(name);

	}

	/**
	 * Add an Item to a Chart
	 * @param name
	 * @param item
	 * @return
	 */
	 @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
	public Chart addItem(@PathVariable String name, @RequestBody Chartitem item) {

		return chartPut.addItem(name, item.getSku(), item.getQuantity());

	}
	 
	/**
	 * Delete an Item from the Chart
	 * @param name
	 * @param item
	 * @return
	 */
	 @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	public Chart deleteItem(@PathVariable String name, @RequestBody Chartitem item) {

		return chartPut.addItem(name, item.getSku(), item.getQuantity());

	}

}
