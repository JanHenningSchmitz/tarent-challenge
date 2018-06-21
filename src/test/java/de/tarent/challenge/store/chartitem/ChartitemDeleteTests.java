package de.tarent.challenge.store.chartitem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.ItemNotInChartException;
import de.tarent.challenge.exeptions.NotEnoughItemsInChartEcxeption;
import de.tarent.challenge.exeptions.chart.quantity.ChartitemQuantityBelowZeroException;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartitemDeleteTests extends ChartControllerTests {

	private Product testproduct2 = null;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		// Creating testdata
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct2 = new Product(this.getClass().getSimpleName() + 2, this.getClass().getSimpleName() + 2, 2.1, true,
				test_product2_eans);
		createTestProduct(testproduct2);
	}

	@Test
	public void deleteItemFromChart() throws Exception {
		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() - expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);
	}

	@Test
	public void deleteLastItemFromChart() throws Exception {
		int quantity = 2;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() - expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);
	}

	@Test
	public void deleteWithZeroItems() throws Exception {
		int quantity = 0;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item)
				.andExpect(status().is(ChartitemQuantityBelowZeroException.STATUS.value()));
		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartitemQuantityBelowZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void deleteToMutchItemsFromChart() throws Exception {

		int quantity = 4;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item)
				.andExpect(status().is(NotEnoughItemsInChartEcxeption.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!NotEnoughItemsInChartEcxeption.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void deleteUnknownItemFromChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct2.getSku(), quantity);

		double expectetPrice = quantity * testproduct2.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() - expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item)
				.andExpect(status().is(ItemNotInChartException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ItemNotInChartException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	private ResultActions deleteItemsFromChart(String chartname, String chartitem) throws Exception {

		return this.mockMvc.perform(delete("/charts/" + chartname + "/delete/" + chartitem).contentType(contentType));
	}
}
