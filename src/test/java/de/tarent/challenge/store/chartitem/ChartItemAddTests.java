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

import de.tarent.challenge.exeptions.chart.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartItemAddTests extends ChartControllerTests {

	private Product newProduct;
	private Product unavailableProduct;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		// Creating testdata
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		newProduct = new Product(this.getClass().getSimpleName() + 2, this.getClass().getSimpleName() + 2, 2.1, true,
				test_product2_eans);
		createTestProduct(newProduct);

		Set<String> test_product3_eans = new HashSet<String>();
		test_product3_eans.addAll(Arrays.asList("00000000", "00000001"));
		unavailableProduct = new Product(this.getClass().getSimpleName() + 3, this.getClass().getSimpleName() + 3, 2.1,
				false, test_product2_eans);
		createTestProduct(unavailableProduct);
	}

	/**
	 * Adding a new item to a Chart
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNewItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(newProduct.getSku(), quantity);

		double expectetPrice = quantity * newProduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);

	}

	/**
	 * Adding an item to the chart that is unavailable and failing
	 * 
	 * @throws Exception
	 */
	@Test
	public void addUnavailableItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(unavailableProduct.getSku(), quantity);

		double expectetPrice = quantity * unavailableProduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	/**
	 * Adding an item thats not exists to the chart and failing
	 * 
	 * @throws Exception
	 */
	@Test
	public void addInvalidItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem("invalid", quantity);

		double expectetPrice = 2;
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	/**
	 * Adding a existing item to the chart => increasing the amount
	 * 
	 * @throws Exception
	 */
	@Test
	public void addExistingItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct.getSku(), quantity);

		double expectetPrice = quantity * testproduct.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);

	}
}
