package de.tarent.challenge.store.products.name;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.name.InvalidProductNameException;
import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.StoreApplicationTests;

/**
 * Test class for product name altering test cases
 * @author Jan-Henning Schmitz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductNameTests extends StoreApplicationTests {

	@Before
	public void setup() throws IOException, Exception {		
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	/**
	 * Change name of a product
	 * @throws Exception
	 */
	public void changeName() throws Exception {

		testproduct.setName("newName");

		String uri = "/products/" + testproduct.getSku() + "/name/" + "newName";

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}
	
	@Test
	/**
	 * Change name of a product
	 * @throws Exception
	 */
	public void changeInvalidName() throws Exception {

		testproduct.setName("newName");

		String uri = "/products/" + testproduct.getSku() + "/name/" + "   ";

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(InvalidProductNameException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidProductNameException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * change name of a product not in DB and fail
	 * @throws Exception
	 */
	@Test
	public void changeNameInvalidSku() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "invalid" + "/name/" + "newName";

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}


}
