package de.tarent.challenge.store.chartitem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartItemAddTests extends ChartControllerTests {

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void addUnavailableItemToChart() throws Exception {

		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[4];
		// Chartitem item = new Chartitem(product.getSku(), 1);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = addItemsToChart(chart.getName(), item)
		// .andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }

	}

	@Test
	public void addExistingItemToChart() throws Exception {

		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 2);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = addItemsToChart(chart.getName(), item);
		// resultActions.andExpect(status().isOk());
		//
		// controllChart(resultActions, chart);

	}

	@Test
	public void addNewItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);

	}

	private ResultActions addItemsToChart(String chartname, String chartitem) throws Exception {
		return this.mockMvc.perform(put("/charts/" + chartname + "/add/" + chartitem).contentType(contentType));
	}

}
