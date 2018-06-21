package de.tarent.challenge.store.chart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.tarent.challenge.store.chart.rest.ChartDelete;
import de.tarent.challenge.store.chart.rest.ChartGet;
import de.tarent.challenge.store.chart.rest.ChartPost;
import de.tarent.challenge.store.chart.rest.ChartPut;
import de.tarent.challenge.store.chart.rest.validation.ChartValidator;
import de.tarent.challenge.store.chart.rest.validation.ChartitemValidator;
import de.tarent.challenge.store.products.ProductService;

@RestController
@RequestMapping("/charts")
public class ChartController {
	private final ChartGet chartGet;
	private final ChartPost chartPost;
	private final ChartPut chartPut;
	private final ChartDelete chartDelete;
	private final ChartValidator chartValidator;
	private final ChartitemValidator chartitemValidator;

	public ChartController(ChartService chartService, ProductService productService) {

		// INFO At this point, no need for validation, so this can be on top.
		// Maybe there have to be some changes later
		this.chartGet = new ChartGet(chartService);

		chartitemValidator = new ChartitemValidator(productService);
		chartValidator = new ChartValidator(chartGet, chartitemValidator);

		this.chartPut = new ChartPut(chartService, chartValidator, chartitemValidator);
		this.chartPost = new ChartPost(chartService, chartValidator);
		this.chartDelete = new ChartDelete(chartService, chartGet);
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
	public ResponseEntity<?> createNewChart(@RequestBody Chart chart) {
		return chartPost.createNewChart(chart);

	}

	/**
	 * Add an Item to a Chart
	 * 
	 * @param name
	 * @param item
	 * @return
	 */
	@RequestMapping(value = "/{name}/checkout", method = RequestMethod.PUT)
	public Chart checkOutChart(@PathVariable String name) {
		return chartPut.checkOutChart(name);
	}

	/**
	 * Add an Item to a Chart
	 * 
	 * @param name
	 * @param item
	 * @return
	 */
	@RequestMapping(value = "/{name}/add/{chartitem}", method = RequestMethod.POST)
	public Chart addItem(@PathVariable String name, @PathVariable String chartitem) {
		return chartPut.addItem(name, chartitem);
	}

	/**
	 * Delete an Item from the Chart
	 * 
	 * @param name
	 * @param item
	 * @return
	 */
	@RequestMapping(value = "/{name}/delete/{chartitem}", method = RequestMethod.DELETE)
	public Chart deleteItem(@PathVariable String name, @PathVariable String chartitem) {

		return chartPut.deleteItem(name, chartitem);

	}

	/**
	 * Delete an Item from the Chart
	 * 
	 * @param name
	 * @param item
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	public void deleteChart(@PathVariable String name) {

		chartDelete.deleteChartByName(name);

	}

	/**
	 * Deleting all Charts, JUST FOR TESTING
	 */
	@DeleteMapping("/all")
	public void deleteAll() {
		chartDelete.deleteAll();

	}

}
