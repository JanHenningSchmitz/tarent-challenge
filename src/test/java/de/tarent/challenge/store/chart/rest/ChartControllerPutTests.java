package de.tarent.challenge.store.chart.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartControllerPutTests extends ChartControllerTests {

	@Test
	public void addExistingItemToChart() throws Exception {
		
		Chart chart = TESTCHARTS[0];
		Product product = TEST_PRODUCTS[0];
		Chartitem item = new Chartitem(product.getSku(), 2);
		
		double expectetPrice = item.getQuantity() * product.getPrice();
		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		
		ResultActions resultActions = addItemsToChart(chart.getName(), item);
		resultActions.andExpect(status().isOk());
		
		controllChart(resultActions, chart);

	}

	@Test
	public void addNewItemToChart() throws Exception {
		
		Chart chart = TESTCHARTS[0];
		Product product = TEST_PRODUCTS[2];
		Chartitem item = new Chartitem(product.getSku(), 1);
		
		double expectetPrice = item.getQuantity() * product.getPrice();
		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		
		ResultActions resultActions = addItemsToChart(chart.getName(), item);
		resultActions.andExpect(status().isOk());
		
		controllChart(resultActions, chart);

	}

	@Test
	public void deleteItemFromChart() throws Exception {

	}

	@Test
	public void deleteLastItemFromChart() throws Exception {

	}

	@Test
	public void changeWithWrongChart() throws Exception {

	}

	private ResultActions addItemsToChart(String chartname, Chartitem chartitem) throws Exception {

		String json = json(chartitem);

		return this.mockMvc.perform(put("/charts/" + chartname).contentType(contentType).content(json));
	}
}
