package de.tarent.challenge.store.chart;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;
import de.tarent.challenge.store.products.Product;

public class ChartControllerTests extends StoreApplicationTests {

	protected void createTestProduct(Product product) throws IOException, Exception {

		this.mockMvc.perform(post("/products").contentType(contentType).content(json(product)))
				.andExpect(status().isCreated());
	}

	protected void controllChart(ResultActions resultActions, Chart chart) throws Exception {

		resultActions.andExpect(jsonPath("$.name", is(chart.getName())))
				.andExpect(jsonPath("$.totalprice", is(chart.getTotalprice())));
		// FIXME Error with containsinanyorder
		// .andExpect(jsonPath("$.chartitems", is(jsonChartitems)));
	}

}
