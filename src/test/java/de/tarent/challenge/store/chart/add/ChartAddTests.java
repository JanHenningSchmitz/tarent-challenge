package de.tarent.challenge.store.chart.add;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.chart.ChartAllreadyInUseException;
import de.tarent.challenge.exeptions.chart.ChartIsEmptyOnCreateException;
import de.tarent.challenge.exeptions.chart.ChartIsNullException;
import de.tarent.challenge.exeptions.chart.ChartNameInvalidException;
import de.tarent.challenge.exeptions.chart.ChartWithWrongTotalPriceException;
import de.tarent.challenge.exeptions.chart.ChartitemStringNotValidException;
import de.tarent.challenge.exeptions.chart.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.exeptions.chart.quantity.ChartitemQuantityBelowZeroException;
import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

/**
 * Test class for chart adding test cases
 * 
 * @author Jan-Henning Schmitz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartAddTests extends ChartControllerTests {

	private Product unavailableProduct;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		// Creating test data
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		unavailableProduct = new Product("unavailableProduct", "unavailableProduct" + 2, 2.1, false,
				test_product2_eans);
		createTestProduct(unavailableProduct);
	}

	/**
	 * Trying to successfully create a new chart
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChart() throws Exception {

		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(testproduct.getSku(), 1));

		Chart chart = new Chart("newtestchart", items, (testproduct.getPrice()));

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().isCreated());

	}

	/**
	 * Adding a Chart with an already taken name
	 * 
	 * @throws Exception
	 */
	@Test
	public void addChartThatIsAllreadyThere() throws Exception {

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(testchart)))
				.andExpect(status().is(ChartAllreadyInUseException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartAllreadyInUseException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * Adding a Chart with a name only build out of Spaces
	 * 
	 * @throws Exception
	 */
	@Test
	public void addChartWithInvalidName() throws Exception {
		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(testproduct.getSku(), 1));

		Chart chart = new Chart(" ", items, (testproduct.getPrice()));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(ChartNameInvalidException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartNameInvalidException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * Creating a Chart with no Chart items
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChartWithoutItems() throws Exception {
		Set<String> tmp_ChartItems = new HashSet<String>();

		Chart chart = new Chart("newtestchart", tmp_ChartItems, 0);

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(ChartIsEmptyOnCreateException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartIsEmptyOnCreateException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * Adding a new Chart with a Item that is unavailable
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChartWithUnavailableItem() throws Exception {

		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(unavailableProduct.getSku(), 1));

		Chart chart = new Chart("newtestchart", items, (unavailableProduct.getPrice()));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	/**
	 * Adding a Chart with a IInvalid SKU Item
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChartWithInvalidSkuItem() throws Exception {

		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem("invalid", 1));

		Chart chart = new Chart("newtestchart", items, (2));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	/**
	 * Adding new chart with an invalid chart item string
	 */
	@Test
	public void deleteInvalidChartItemString() throws Exception {

		Set<String> items = new HashSet<String>();
		items.add("invalid");

		Chart chart = new Chart("newtestchart", items, (2));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartitemStringNotValidException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * Creating a Chart with an Chart item that has Zero Quantity's
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChartWithItemQuantityBelowZero() throws Exception {

		// The testing for invalid products is in ProductControllerTests

		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(testproduct.getSku(), 0));

		Chart chart = new Chart("newtestchart", items, (0));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(ChartitemQuantityBelowZeroException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartitemQuantityBelowZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * Creating a Chart with an Chart item that has Zero Quantity's
	 * 
	 * @throws Exception
	 */
	@Test
	public void creatingAChartWithChartIsNull() throws Exception {

		this.mockMvc.perform(post("/charts").contentType(contentType))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Creating a Chart with a wrong total price
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewChartWithWrongTotalprice() throws Exception {
		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(testproduct.getSku(), 1));

		Chart chart = new Chart("newtestchart", items, (testproduct.getPrice() * 5));

		ResultActions resultActions = this.mockMvc
				.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().is(ChartWithWrongTotalPriceException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartWithWrongTotalPriceException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
