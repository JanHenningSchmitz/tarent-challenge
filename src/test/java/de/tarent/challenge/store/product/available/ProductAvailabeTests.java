package de.tarent.challenge.store.product.available;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.SkuNotFoundException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAvailabeTests extends ProductControllerTests {

	private Product testproduct = null;

	@Before
	public void setup() throws IOException, Exception {

		super.setup();

		// Creating testdata
		Set<String> test_product_eans = new HashSet<String>();
		test_product_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct = new Product("ProductAvailabeTests", "ProductAvailabeTests", 2.1, true, test_product_eans);
		createTestProduct(testproduct);
	}

	@Test
	public void changeAvailable() throws Exception {

		testproduct.setAvailable(false);

		String uri = "/products/" + testproduct.getSku() + "/available/" + false;

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}

	@Test
	public void changeAvailableInvalidSku() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "invalid" + "/available/" + true;

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
