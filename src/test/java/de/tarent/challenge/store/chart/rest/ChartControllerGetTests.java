package de.tarent.challenge.store.chart.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.chart.ChartControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerGetTests extends ChartControllerTests {

	@Test
	public void readChartByName() throws Exception {

		// Chart chartTESTCHARTS = [0];
		//
		// ResultActions resultActions = mockMvc.perform(get("/charts/" +
		// chart.getName()));
		//
		// resultActions.andExpect(status().isOk()).andExpect(content().contentType(contentType));
		//
		// controllChart(resultActions, chart);

	}

	@Test
	public void readChartByInvalidName() throws Exception {

		// ResultActions resultActions = mockMvc.perform(get("/charts/notThere"));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ChartNotFoundException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	@Test
	public void readCharts() throws Exception {
		// // At first check if the size ist valid
		// ResultActions resultActions =
		// mockMvc.perform(get("/charts")).andExpect(status().isOk())
		// .andExpect(content().contentType(contentType)).andExpect(jsonPath("$",
		// hasSize(TESTCHARTS.length)));
		//
		// // Now Check the Content
		// for (int i = 0; i < TESTCHARTS.length; i++) {
		//
		// resultActions.andExpect(jsonPath("$[" + i + "].name",
		// is(TESTCHARTS[i].getName())))
		// .andExpect(jsonPath("$[" + i + "].totalprice",
		// is(TESTCHARTS[i].getTotalprice())));
		// // FIXME Error with containsinanyorder
		// // .andExpect(jsonPath("$[" + i + "].chartitems",
		// // containsInAnyOrder(TESTCHARTS[i].getChartitems().toArray(new
		// // Chartitem[0]))));
		// }
	}

}
