package de.tarent.challenge.store.products;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;

public class ProductControllerTests extends StoreApplicationTests {

	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		// Delete everything thats there to set up a new Test DB
		this.mockMvc.perform(delete("/products/all"));
	}

	protected void createTestProduct(Product product) throws IOException, Exception {

		this.mockMvc.perform(post("/products").contentType(contentType).content(json(product)))
				.andExpect(status().isCreated());
	}

	protected void controllProduct(ResultActions resultActions, Product product) throws Exception {
		resultActions.andExpect(jsonPath("$.sku", is(product.getSku())))
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.available", is(product.isAvailable())))
				.andExpect(jsonPath("$.eans", containsInAnyOrder(product.getEans().toArray(new String[0]))));
	}

}
