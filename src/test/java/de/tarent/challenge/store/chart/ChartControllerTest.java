package de.tarent.challenge.store.chart;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.StoreApplicationTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerTest extends StoreApplicationTests {

	@Test
	public void createNewChartAndAddItems() throws Exception {

		double expectetPrice = 2 * TEST_PRODUCTS[0].getPrice();
		List<Chartitem> chartItems = new ArrayList<Chartitem>();
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

		expectetPrice += 2 * TEST_PRODUCTS[1].getPrice();
		addItemsToChart(TEST_PRODUCTS[1], testChart, 2);
		readChart(testChart.getName(), expectetPrice);

		expectetPrice += TEST_PRODUCTS[2].getPrice() * 5;
		addItemsToChart(TEST_PRODUCTS[2], testChart, 5);
		readChart(testChart.getName(), expectetPrice);
	}

	private void readChart(String name, double expectetPrice) throws Exception {
		this.mockMvc.perform(get("/charts/" + name)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.totalprice", is(expectetPrice)));
		// TODO Numbers of Elements should also be checked
		// .andExpect(jsonPath("$.eans", containsInAnyOrder(tmp_EANS.toArray(new
		// String[0]))));
	}

	private void addItemsToChart(Product product, Chart chart, int quantity) throws Exception {

		Chartitem item = new Chartitem(product.getSku(), quantity);
		String json = json(item);

		this.mockMvc.perform(put("/charts/" + chart.getName()).contentType(contentType).content(json))
				.andExpect(status().isOk());
	}

}
