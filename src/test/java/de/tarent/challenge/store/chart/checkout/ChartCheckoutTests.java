package de.tarent.challenge.store.chart.checkout;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.chart.ChartControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartCheckoutTests extends ChartControllerTests {

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void checkOutChart() throws Exception {
		this.mockMvc.perform(put("/charts/" + testchart.getName() + "/checkout").contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void addToCheckedOutChart() throws Exception {
		// Chart chart = TESTCHARTS[2];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 4);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = addItemsToChart(chart.getName(), item)
		// .andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	@Test
	public void deleteFromToCheckedOutChart() throws Exception {
		// Chart chart = TESTCHARTS[2];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 4);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
		// .andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	@Test
	public void checkOutWithUnavailableProducts() throws Exception {

		// TEST_PRODUCTS[2].setAvailable(false);
		//
		// this.mockMvc.perform(
		// put("/products/" +
		// TEST_PRODUCTS[2].getSku()).contentType(contentType).content(json(TEST_PRODUCTS[2])))
		// .andExpect(status().isOk());
		//
		// ResultActions resultActions = this.mockMvc
		// .perform(put("/charts/" + TESTCHARTS[3].getName() +
		// "/checkout").contentType(contentType))
		// .andExpect(status().is(ProductIsNotAvailableForCheckOutException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ProductIsNotAvailableForCheckOutException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }

	}
}