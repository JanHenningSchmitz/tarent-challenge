package de.tarent.challenge.store.chart.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.store.chart.ChartControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerDeleteTests extends ChartControllerTests {

	@Test
	public void deleteAllProducts() throws Exception {

		this.mockMvc.perform(delete("/charts/all")).andExpect(status().isOk());
	}

	@Test
	public void deleteChartByName() throws Exception {

		this.mockMvc.perform(delete("/charts/" + TESTCHARTS[0].getName()).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteChartByNameThatsNotThere() throws Exception {

		ResultActions resultActions = this.mockMvc.perform(delete("/charts/" + "noname").contentType(contentType))
				.andExpect(status().is(ChartNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
