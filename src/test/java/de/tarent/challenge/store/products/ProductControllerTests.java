package de.tarent.challenge.store.products;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;

public class ProductControllerTests extends StoreApplicationTests {

	protected void controllProduct(ResultActions resultActions, Product product) throws Exception {
		resultActions.andExpect(jsonPath("$.sku", is(product.getSku())))
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.available", is(product.isAvailable())))
				.andExpect(jsonPath("$.eans", containsInAnyOrder(product.getEans().toArray(new String[0]))));
	}

}
