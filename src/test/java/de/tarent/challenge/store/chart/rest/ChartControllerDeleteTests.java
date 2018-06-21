package de.tarent.challenge.store.chart.rest;

import org.junit.Test;

import de.tarent.challenge.store.chart.ChartControllerTests;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ChartControllerDeleteTests extends ChartControllerTests {

	@Test
	public void deleteAllProducts() throws Exception {

		// this.mockMvc.perform(delete("/charts/all")).andExpect(status().isOk());
	}

	@Test
	public void deleteChartByName() throws Exception {

		// this.mockMvc.perform(delete("/charts/" +
		// TESTCHARTS[0].getName()).contentType(contentType))
		// .andExpect(status().isOk());
	}

	@Test
	public void deleteChartByNameThatsNotThere() throws Exception {

		// ResultActions resultActions = this.mockMvc.perform(delete("/charts/" +
		// "noname").contentType(contentType))
		// .andExpect(status().is(ChartNotFoundException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ChartNotFoundException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

}
