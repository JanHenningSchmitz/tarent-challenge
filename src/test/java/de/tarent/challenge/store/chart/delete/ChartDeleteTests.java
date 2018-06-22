package de.tarent.challenge.store.chart.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ChartNotFoundException;
import de.tarent.challenge.store.chart.ChartControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartDeleteTests extends ChartControllerTests {

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());
	}
	@Test
	public void deleteChartByName() throws Exception {

		 this.mockMvc.perform(delete("/charts/" + testchart.getName()).contentType(contentType))
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
