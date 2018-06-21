package de.tarent.challenge.store.chart.add;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ChartAllreadyInUseException;
import de.tarent.challenge.exeptions.ChartIsEmptyOnCreateException;
import de.tarent.challenge.exeptions.ChartNameInvalidException;
import de.tarent.challenge.exeptions.ChartWithWrongTotalPriceException;
import de.tarent.challenge.exeptions.chart.quantity.ChartitemQuantityBelowZeroException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartAddTests extends ChartControllerTests {

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void addNewChart() throws Exception {

		Set<String> items = new HashSet<String>();
		items.add(Chartitem.createChartitem(testproduct.getSku(), 1));

		Chart chart = new Chart("newtestchart", items, (testproduct.getPrice()));

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().isCreated());

	}

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

	@Test
	public void addNewChartWithUnavailableItem() throws Exception {

		// // The testing for invalid products is in ProductControllerTests
		//
		// Set<Chartitem> tmp_ChartItems = new HashSet<Chartitem>();
		// tmp_ChartItems.add(new Chartitem(TEST_PRODUCTS[4].getSku(), 1));
		//
		// Chart chart = new Chart("newtestchart", tmp_ChartItems,
		// (TEST_PRODUCTS[4].getPrice()));
		//
		// ResultActions resultActions = this.mockMvc
		// .perform(post("/charts").contentType(contentType).content(json(chart)))
		// .andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

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
