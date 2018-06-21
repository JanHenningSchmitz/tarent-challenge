package de.tarent.challenge.store.chart.checkout;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import de.tarent.challenge.exeptions.ChartIsCheckedOutException;
import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.ChartControllerTests;
import de.tarent.challenge.store.chart.item.Chartitem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartCheckoutTests extends ChartControllerTests {

	protected Chart testchart2 = null;

	@Before
	public void setup() throws Exception {
		super.setup(this.getClass().getSimpleName());

		Set<String> tmp_ChartItems = new HashSet<String>();
		tmp_ChartItems.add(Chartitem.createChartitem(this.testproduct.getSku(), 1));

		testchart2 = new Chart(this.getClass().getSimpleName() + 2, tmp_ChartItems, (this.testproduct.getPrice()));
		testchart2.setCheckedout();
		createTestChart(testchart2);
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
		testchart2.setTotalprice(testchart2.getTotalprice() + expectetPrice);

		ResultActions resultActions = addItemsToChart(testchart2.getName(), item)
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
		testchart2.setTotalprice(testchart2.getTotalprice() + expectetPrice);

		ResultActions resultActions = deleteItemsFromChart(testchart2.getName(), item)
				.andExpect(status().is(ChartIsCheckedOutException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ChartIsCheckedOutException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void checkOutWithUnavailableProducts() throws Exception {

		// TODO Write this test
		
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

	private ResultActions addItemsToChart(String chartname, String chartitem) throws Exception {
		return this.mockMvc.perform(post("/charts/" + chartname + "/add/" + chartitem).contentType(contentType));
	}

	private ResultActions deleteItemsFromChart(String chartname, String chartitem) throws Exception {

		return this.mockMvc.perform(delete("/charts/" + chartname + "/delete/" + chartitem).contentType(contentType));
	}
}