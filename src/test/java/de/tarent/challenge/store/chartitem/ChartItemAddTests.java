package de.tarent.challenge.store.chartitem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import de.tarent.challenge.exeptions.ProductIsNotAvailableForAddingException;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartItemAddTests extends ChartControllerTests {

	private Product testproduct2;
	private Product testproduct3;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		// Creating testdata
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct2 = new Product(this.getClass().getSimpleName() + 2, this.getClass().getSimpleName() + 2, 2.1, true,
				test_product2_eans);
		createTestProduct(testproduct2);

		Set<String> test_product3_eans = new HashSet<String>();
		test_product3_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct3 = new Product(this.getClass().getSimpleName() + 3, this.getClass().getSimpleName() + 3, 2.1, false,
				test_product2_eans);
		createTestProduct(testproduct3);
	}

	@Test
	public void addUnavailableItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct3.getSku(), quantity);

		double expectetPrice = quantity * testproduct3.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().is(ProductIsNotAvailableForAddingException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductIsNotAvailableForAddingException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

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

	@Test
	public void addNewItemToChart() throws Exception {

		int quantity = 1;
		String item = Chartitem.createChartitem(testproduct2.getSku(), quantity);

		double expectetPrice = quantity * testproduct2.getPrice();
		testchart.setTotalprice(testchart.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart.getName(), item);
		resultActions.andExpect(status().isOk());

		controllChart(resultActions, testchart);

	}

	private ResultActions addItemsToChart(String chartname, String chartitem) throws Exception {
		return this.mockMvc.perform(post("/charts/" + chartname + "/add/" + chartitem).contentType(contentType));
	}

}
