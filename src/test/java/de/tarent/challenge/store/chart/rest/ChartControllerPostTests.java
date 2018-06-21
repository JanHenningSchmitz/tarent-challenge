package de.tarent.challenge.store.chart.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.chart.ChartControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerPostTests extends ChartControllerTests {

	@Test
	public void addItemsToChart() throws Exception {

		// Chartitem item = new Chartitem(TEST_PRODUCTS[2].getSku(), 2);
		// String json = json(item);
		//
		// this.mockMvc.perform(put("/charts/" +
		// TESTCHARTS[1].getName()).contentType(contentType).content(json))
		// .andExpect(status().isOk());
	}

}
