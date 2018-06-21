package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.EanIsEmptyException;
import de.tarent.challenge.exeptions.InvalidProductNameException;
import de.tarent.challenge.exeptions.NoEansException;
import de.tarent.challenge.exeptions.PriceLowerZeroException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllterPutTest extends ProductControllerTests {

	@Test
	public void changeWithWrongSku() throws Exception {
		// TODO

	}

	@Test
	public void changeProductPrice() throws Exception {

		Product product = TEST_PRODUCTS[0];
		product.setPrice(TEST_PRODUCTS[0].getPrice() * 2);

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void changeInvalidProductPrice() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.setPrice(0);

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().is(PriceLowerZeroException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!PriceLowerZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void changeProductName() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.setName(TEST_PRODUCTS[0].getName() + "new");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void changeInvalidProductName() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.setName("   ");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().is(InvalidProductNameException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidProductNameException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void changeNullProductName() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.setName(null);

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().is(InvalidProductNameException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidProductNameException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void addEansToProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().add("NewEan");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void addEmptyEansToProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().add("   ");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().is(EanIsEmptyException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!EanIsEmptyException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void removeEansFromProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().remove("00000000");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void removeAllEansFromProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().clear();

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json))
				.andExpect(status().is(NoEansException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!NoEansException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
