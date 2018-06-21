package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllterPutTest extends ProductControllerTests {

	@Test
	public void changeProductPrice() throws Exception {

		Product product = TEST_PRODUCTS[0];
		product.setPrice(TEST_PRODUCTS[0].getPrice() * 2);

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json)).andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void changeProductName() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.setName(TEST_PRODUCTS[0].getName()+"new");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json)).andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void addEansToProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().add("NewEan");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json)).andExpect(status().isOk());

		controllProduct(resultActions, product);
	}

	@Test
	public void removeEansFromProduct() throws Exception {
		Product product = TEST_PRODUCTS[0];
		product.getEans().remove("00000000");

		String json = json(product);

		ResultActions resultActions = this.mockMvc
				.perform(put("/products/" + product.getSku()).contentType(contentType).content(json)).andExpect(status().isOk());

		controllProduct(resultActions, product);
	}
}
