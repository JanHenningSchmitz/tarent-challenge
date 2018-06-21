package de.tarent.challenge.store.chart.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ChartIsCheckedOutException;
import de.tarent.challenge.exeptions.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.exeptions.ProductIsNotAvailableForCheckOutException;
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
		Chart chart = TESTCHARTS[0];
		Product product = TEST_PRODUCTS[0];
		Chartitem item = new Chartitem(product.getSku(), 1);

		double expectetPrice = item.getQuantity() * product.getPrice();
		chart.setTotalprice(chart.getTotalprice() + expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, chart);
	}

//	@Test
//	public void deleteLastItemFromChart() throws Exception {
//		Chart chart = TESTCHARTS[0];
//		Product product = TEST_PRODUCTS[0];
//		Chartitem item = new Chartitem(product.getSku(), 3);
//
//		double expectetPrice = item.getQuantity() * product.getPrice();
//		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
//
//		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item);
//		resultActions.andExpect(status().isOk());
//
//		controllChart(resultActions, chart);
//	}

//	@Test
//	public void deleteWithZeroItems() throws Exception {
//		Chart chart = TESTCHARTS[0];
//		Product product = TEST_PRODUCTS[0];
//		Chartitem item = new Chartitem(product.getSku(), 0);
//
//		double expectetPrice = product.getPrice();
//		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
//
//		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
//				.andExpect(status().is(QuantityBelowZeroException.STATUS.value()));
//		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
//		if (!QuantityBelowZeroException.MESSAGE.equals(errorMsg)) {
//			throw new Exception(errorMsg);
//		}
//	}

//	@Test
//	public void deleteToMutchItemsFromChart() throws Exception {
//		Chart chart = TESTCHARTS[0];
//		Product product = TEST_PRODUCTS[0];
//		Chartitem item = new Chartitem(product.getSku(), 4);
//
//		double expectetPrice = item.getQuantity() * product.getPrice();
//		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
//
//		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
//				.andExpect(status().is(NotEnoughItemsInChartEcxeption.STATUS.value()));
//
//		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
//		if (!NotEnoughItemsInChartEcxeption.MESSAGE.equals(errorMsg)) {
//			throw new Exception(errorMsg);
//		}
//	}

//	@Test
//	public void deleteUnknownItemFromChart() throws Exception {
//		Chart chart = TESTCHARTS[0];
//		Product product = TEST_PRODUCTS[3];
//		Chartitem item = new Chartitem(product.getSku(), 4);
//
//		double expectetPrice = item.getQuantity() * product.getPrice();
//		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
//
//		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
//				.andExpect(status().is(ItemNotInChartException.STATUS.value()));
//
//		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
//		if (!ItemNotInChartException.MESSAGE.equals(errorMsg)) {
//			throw new Exception(errorMsg);
//		}
//	}

	@Test
	public void addUnavailableItemToChart() throws Exception {

		Chart chart = TESTCHARTS[0];
		Product product = TEST_PRODUCTS[4];
		Chartitem item = new Chartitem(product.getSku(), 1);

		double expectetPrice = item.getQuantity() * product.getPrice();
		chart.setTotalprice(chart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(chart.getName(), item)
				.andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	@Test
	public void checkOutChart() throws Exception {
		this.mockMvc.perform(put("/charts/" + TESTCHARTS[0].getName() + "/checkout").contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void checkOutWithUnavailableProducts() throws Exception {

		TEST_PRODUCTS[2].setAvailable(false);

		this.mockMvc.perform(
				put("/products/" + TEST_PRODUCTS[2].getSku()).contentType(contentType).content(json(TEST_PRODUCTS[2])))
				.andExpect(status().isOk());

		ResultActions resultActions = this.mockMvc
				.perform(put("/charts/" + TESTCHARTS[3].getName() + "/checkout").contentType(contentType))
				.andExpect(status().is(ProductIsNotAvailableForCheckOutException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForCheckOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	@Test
	public void addToCheckedOutChart() throws Exception {
		Chart chart = TESTCHARTS[2];
		Product product = TEST_PRODUCTS[0];
		Chartitem item = new Chartitem(product.getSku(), 4);

		double expectetPrice = item.getQuantity() * product.getPrice();
		chart.setTotalprice(chart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(chart.getName(), item)
				.andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

//	@Test
//	public void deleteFromToCheckedOutChart() throws Exception {
//		Chart chart = TESTCHARTS[2];
//		Product product = TEST_PRODUCTS[0];
//		Chartitem item = new Chartitem(product.getSku(), 4);
//
//		double expectetPrice = item.getQuantity() * product.getPrice();
//		chart.setTotalprice(chart.getTotalprice() + expectetPrice);
//
//		ResultActions resultActions = deleteItemsFromChart(chart.getName(), item)
//				.andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));
//
//		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
//		if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
//			throw new Exception(errorMsg);
//		}
//	}

	@Test
	public void changeWithWrongChart() throws Exception {
		// TODO
	}

	private ResultActions deleteItemsFromChart(String chartname, Chartitem chartitem) throws Exception {

		String json = json(chartitem);

		return this.mockMvc.perform(put("/charts/" + chartname + "/delete").contentType(contentType).content(json));
	}

	private ResultActions addItemsToChart(String chartname, Chartitem chartitem) throws Exception {

		String json = json(chartitem);

		return this.mockMvc.perform(put("/charts/" + chartname + "/add").contentType(contentType).content(json));
	}
}
