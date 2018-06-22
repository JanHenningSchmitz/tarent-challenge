package de.tarent.challenge.store.product.available;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.StoreApplicationTests;

/**
 * Test class for product available altering test cases
 * 
 * @author Jan-Henning Schmitz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAvailabeTests extends StoreApplicationTests {

	@Before
	public void setup() throws IOException, Exception {
		super.setup(this.getClass().getSimpleName());
	}

	/**
	 * Changing the availability of a product
	 * 
	 * @throws Exception
	 */
	@Test
	public void changeAvailable() throws Exception {

		testproduct.setAvailable(false);

		String uri = "/products/" + testproduct.getSku() + "/available/" + false;

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}

	/**
	 * Changing the availability of a invalid product and fail
	 * 
	 * @throws Exception
	 */
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
