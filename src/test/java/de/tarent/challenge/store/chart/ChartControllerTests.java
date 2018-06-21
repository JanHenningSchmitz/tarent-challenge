package de.tarent.challenge.store.chart;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;
import de.tarent.challenge.store.chart.item.Chartitem;

public class ChartControllerTests extends StoreApplicationTests {

	/**
	 * This Method is invoked before EVERY Test run!,
	 * 
	 * @throws Exception
	 */
	@Before
	public void setupCharts() throws Exception {
		super.setupProducts();

		// Delete everything thats there to set up a new Test DB
		mockMvc.perform(delete("/charts/all"));

		Set<Chartitem> tmp_ChartItems = new HashSet<Chartitem>();
		tmp_ChartItems.add(new Chartitem(TEST_PRODUCTS[0].getSku(), 1));
		tmp_ChartItems.add(new Chartitem(TEST_PRODUCTS[1].getSku(), 1));

		TESTCHARTS[0] = new Chart("testChart0", tmp_ChartItems,
				(TEST_PRODUCTS[0].getPrice() + TEST_PRODUCTS[1].getPrice()));

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(TESTCHARTS[0])))
				.andExpect(status().isCreated());

		tmp_ChartItems = new HashSet<Chartitem>();
		tmp_ChartItems.add(new Chartitem(TEST_PRODUCTS[2].getSku(), 2));
		tmp_ChartItems.add(new Chartitem(TEST_PRODUCTS[3].getSku(), 2));

		TESTCHARTS[1] = new Chart("testChart1", tmp_ChartItems,
				(TEST_PRODUCTS[2].getPrice() * 2 + TEST_PRODUCTS[3].getPrice() * 2));

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(TESTCHARTS[1])))
				.andExpect(status().isCreated());

	}

	protected void controllChart(ResultActions resultActions, Chart chart) throws Exception {

		// String jsonChartitems = "[";
		// for(Chartitem item : chart.getChartitems()) {
		// jsonChartitems += item.getSku() + "" + item.getQuantity();
		// }
		// jsonChartitems += "]";

		resultActions.andExpect(jsonPath("$.name", is(chart.getName())))
				.andExpect(jsonPath("$.totalprice", is(chart.getTotalprice())));
		// FIXME Error with containsinanyorder
		// .andExpect(jsonPath("$.chartitems", is(jsonChartitems)));
	}

}
