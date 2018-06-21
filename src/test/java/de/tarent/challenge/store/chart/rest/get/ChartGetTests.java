package de.tarent.challenge.store.chart.rest.get;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartGetTests extends ChartControllerTests {

	protected Chart testchart2 = null;

	/**
	 * This Method is invoked before EVERY Test run!,
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		Set<String> tmp_ChartItems = new HashSet<String>();
		tmp_ChartItems.add(Chartitem.createChartitem(this.testproduct.getSku(), 1));

		testchart2 = new Chart(this.getClass().getSimpleName() + 2, tmp_ChartItems, (this.testproduct.getPrice()));
		createTestChart(testchart2);
	}

	@Test
	public void readChartByName() throws Exception {

		Chart chart = testchart;

		ResultActions resultActions = mockMvc.perform(get("/charts/" + chart.getName()));

		resultActions.andExpect(status().isOk()).andExpect(content().contentType(contentType));

		controllChart(resultActions, chart);

	}

	@Test
	public void readChartByInvalidName() throws Exception {

		ResultActions resultActions = mockMvc.perform(get("/charts/notThere"));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void readCharts() throws Exception {
		Chart[] charts = new Chart[] { testchart, testchart2 };

		// At first check if the size ist valid
		ResultActions resultActions = mockMvc.perform(get("/charts")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(charts.length)));

		// Now Check the Content
		for (int i = 0; i < charts.length; i++) {

			resultActions.andExpect(jsonPath("$[" + i + "].name", is(charts[i].getName())))
					.andExpect(jsonPath("$[" + i + "].totalprice", is(charts[i].getTotalprice())));
			// FIXME Error with containsinanyorder
			// .andExpect(jsonPath("$[" + i + "].chartitems",
			// containsInAnyOrder(TESTCHARTS[i].getChartitems().toArray(new
			// Chartitem[0]))));
		}
	}
}
