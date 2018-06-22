package de.tarent.challenge.store.chartitem;

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

import de.tarent.challenge.exeptions.chart.ChartNotFoundException;
import de.tarent.challenge.exeptions.chart.ItemNotInChartException;
import de.tarent.challenge.exeptions.chart.NotEnoughItemsInChartEcxeption;
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

		// Creating test data
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct2 = new Product(this.getClass().getSimpleName() + 2, this.getClass().getSimpleName() + 2, 2.1, true,
				test_product2_eans);
		createTestProduct(testproduct2);
	}

	/**
	 * Successfully decrease the amount of an item from the Chart
	 * 
	 * @throws Exception
	 */
	@Test
	public void decreaseItemFromChart() throws Exception {
		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() - expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);
	}

	/**
	 * Successfully delete the last amount of an item from the Chart => Chart is
	 * deleted
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteLastItemFromChart() throws Exception {
		int quantity = 2;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() - expectetPrice);

		// Deleting the last item
		deleteItemsFromChart(testchart.getName(), item).andExpect(status().isOk());

		// Trying to read the chart and failing
		ResultActions resultActions = super.readChartByName(testchart);
		resultActions.andExpect(status().is(ChartNotFoundException.STATUS.value()));
		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	/**
	 * Deleting with an item without a vaild quantity and failing
	 * @throws Exception
	 */
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

	/**
	 * Delet more Items than there are in the chart and failing
	 * @throws Exception
	 */
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

	/**
	 * Deleting an unknown item from the chart and failing
	 * @throws Exception
	 */
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
	
	/**
	 * Deleting with an invalid chart item string
	 */
	@Test
	public void deleteInvalidChartItemString() throws Exception {

		ResultActions resultActions = deleteItemsFromChart(testchart.getName(), "invalid")
				.andExpect(status().is(ItemNotInChartException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ItemNotInChartException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}
}
