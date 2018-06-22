package de.tarent.challenge.store.products.price;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.price.PriceLowerZeroException;
import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.StoreApplicationTests;

/**
 * Test class for product price altering test cases
 * 
 * @author Jan-Henning Schmitz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPriceTests extends StoreApplicationTests {

	@Before
	public void setup() throws IOException, Exception {

		super.setup(this.getClass().getSimpleName());
	}

	/**
	 * Change price of a product
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePrice() throws Exception {

		testproduct.setPrice((testproduct.getPrice() * 2));

		String uri = "/products/" + testproduct.getSku() + "/price/" + testproduct.getPrice();

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}

	/**
	 * try to change price of a product not in DB and fail
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePriceInvalidSku() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "invalid" + "/price/" + (testproduct.getPrice() * 2);

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * try to change price to 0 and fail
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePriceToZero() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "/price/" + 0;

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(PriceLowerZeroException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!PriceLowerZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	/**
	 * try to change price to below 0 and fail
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePriceToBelowZero() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "/price/" + -5;

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(PriceLowerZeroException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!PriceLowerZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
