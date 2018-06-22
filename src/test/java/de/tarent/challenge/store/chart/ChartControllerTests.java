package de.tarent.challenge.store.chart;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;

public class ChartControllerTests extends StoreApplicationTests {

	/**
	 * Check the given Results for his content and if its equals to the chart
	 * 
	 * @param resultActions
	 * @param chart
	 * @throws Exception
	 */
	protected void controllChart(ResultActions resultActions, Chart chart) throws Exception {

		resultActions.andExpect(jsonPath("$.name", is(chart.getName())))
				.andExpect(jsonPath("$.totalprice", is(chart.getTotalprice())));
		// FIXME Error with containsinanyorder
		// .andExpect(jsonPath("$.chartitems", is(jsonChartitems)));
	}

	protected ResultActions readChartByName(Chart chart) throws Exception {

		return mockMvc.perform(get("/charts/" + chart.getName()));

	}

	protected ResultActions addItemsToChart(String chartname, String chartitem) throws Exception {
		return this.mockMvc.perform(post("/charts/" + chartname + "/add/" + chartitem).contentType(contentType));
	}

	protected ResultActions deleteItemsFromChart(String chartname, String chartitem) throws Exception {
		return this.mockMvc.perform(delete("/charts/" + chartname + "/delete/" + chartitem).contentType(contentType));
	}
}
