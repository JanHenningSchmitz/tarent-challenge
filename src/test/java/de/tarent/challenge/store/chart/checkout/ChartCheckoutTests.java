package de.tarent.challenge.store.chart.checkout;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.chart.ChartIsCheckedOutException;
import de.tarent.challenge.exeptions.chart.ProductIsNotAvailableForCheckOutException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartCheckoutTests extends ChartControllerTests {

	protected Chart checkedOutTestChart;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		Set<String> tmp_ChartItems = new HashSet<String>();
		tmp_ChartItems.add(Chartitem.createChartitem(this.testproduct.getSku(), 1));

		checkedOutTestChart = new Chart(this.getClass().getSimpleName() + 2, tmp_ChartItems,
				(this.testproduct.getPrice()));
		checkedOutTestChart.setCheckedout();
		createTestChart(checkedOutTestChart);
	}

	@Test
	public void checkOutChart() throws Exception {
		this.mockMvc.perform(put("/charts/" + testchart.getName() + "/checkout").contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void addToCheckedOutChart() throws Exception {
		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		checkedOutTestChart.setTotalprice(checkedOutTestChart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(checkedOutTestChart.getName(), item)
				.andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void deleteFromToCheckedOutChart() throws Exception {
		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		checkedOutTestChart.setTotalprice(checkedOutTestChart.getTotalprice() + expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(checkedOutTestChart.getName(), item)
				.andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void checkOutWithUnavailableProducts() throws Exception {

		// TODO Write this test

		// Adding Item
		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);
		addItemsToChart(testchart.getName(), item).andExpect(status().isOk());

		// Making Item Unavailable
		testproduct.setAvailable(false);
		String uri = "/products/" + testproduct.getSku() + "/available/" + false;
		ResultActions resultActionsAvailable = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().isOk());
		controllProduct(resultActionsAvailable, testproduct);

		// Checking out
		ResultActions resultActionsCheckOut = this.mockMvc
				.perform(put("/charts/" + testchart.getName() + "/checkout").contentType(contentType))
				.andExpect(status().is(ProductIsNotAvailableForCheckOutException.STATUS.value()));

		String errorMsg = resultActionsCheckOut.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForCheckOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}
}