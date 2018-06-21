package de.tarent.challenge.store.chart.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerPutTests extends ChartControllerTests {

	@Test
	public void createNewChart() throws Exception {

		double expectetPrice = 2 * TEST_PRODUCTS[0].getPrice();
		Set<Chartitem> chartItems = new HashSet<Chartitem>();
		chartItems.add(new Chartitem(TEST_PRODUCTS[0].getSku(), 2));

		String testUserName = "TestUser";
		Chart testChart = new Chart(testUserName, chartItems, expectetPrice);

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(testChart)))
				.andExpect(status().isCreated());

		this.mockMvc.perform(get("/charts/" + testChart.getName())).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is(testChart.getName())))
				.andExpect(jsonPath("$.totalprice", is(expectetPrice)));
		// TODO Numbers of Elements should also be checked
		// .andExpect(jsonPath("$.chartitems", containsInAnyOrder((new String[0]))));

//		expectetPrice += 2 * TEST_PRODUCTS[1].getPrice();
//		addItemsToChart(TEST_PRODUCTS[1], testChart, 2);
//		readChart(testChart.getName(), expectetPrice);
//
//		expectetPrice += TEST_PRODUCTS[2].getPrice() * 5;
//		addItemsToChart(TEST_PRODUCTS[2], testChart, 5);
//		readChart(testChart.getName(), expectetPrice);
	}
	
}
