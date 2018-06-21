package de.tarent.challenge.store.chartitem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartitemDeleteTests extends ChartControllerTests {

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void deleteItemFromChart() throws Exception {
		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 1);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item);
		// resultActions.andExpect(status().isOk());
		//
		// controllChart(resultActions, chart);
	}

	@Test
	public void deleteLastItemFromChart() throws Exception {
		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 3);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item);
		// resultActions.andExpect(status().isOk());
		//
		// controllChart(resultActions, chart);
	}

	@Test
	public void deleteWithZeroItems() throws Exception {
		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 0);
		//
		// double expectetPrice = product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
		// .andExpect(status().is(QuantityBelowZeroException.STATUS.value()));
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!QuantityBelowZeroException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	@Test
	public void deleteToMutchItemsFromChart() throws Exception {
		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[0];
		// Chartitem item = new Chartitem(product.getSku(), 4);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
		// .andExpect(status().is(NotEnoughItemsInChartEcxeption.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!NotEnoughItemsInChartEcxeption.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	@Test
	public void deleteUnknownItemFromChart() throws Exception {
		// Chart chart = TESTCHARTS[0];
		// Product product = TEST_PRODUCTS[3];
		// Chartitem item = new Chartitem(product.getSku(), 4);
		//
		// double expectetPrice = item.getQuantity() * product.getPrice();
		// chart.setTotalprice(chart.getTotalprice() + expectetPrice);
		//
		// ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
		// .andExpect(status().is(ItemNotInChartException.STATUS.value()));
		//
		// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		// if (!ItemNotInChartException.MESSAGE.equals(errorMsg)) {
		// throw new Exception(errorMsg);
		// }
	}

	private ResultActions deleteItemsFromChart(String chartname, Chartitem chartitem) throws Exception {

		String json = json(chartitem);

		return this.mockMvc.perform(put("/charts/" + chartname + "/delete").contentType(contentType).content(json));
	}
}
