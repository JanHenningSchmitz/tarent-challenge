package de.tarent.challenge.store.chart;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;

public class ChartControllerTests extends StoreApplicationTests {

	protected void controllChart(ResultActions resultActions, Chart chart) throws Exception {

		resultActions.andExpect(jsonPath("$.name", is(chart.getName())))
				.andExpect(jsonPath("$.totalprice", is(chart.getTotalprice())));
		// FIXME Error with containsinanyorder
		// .andExpect(jsonPath("$.chartitems", is(jsonChartitems)));
	}

}
