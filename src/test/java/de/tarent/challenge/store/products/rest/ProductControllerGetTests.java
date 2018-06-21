package de.tarent.challenge.store.products.rest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.SkuNotFoundException;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerGetTests extends ProductControllerTests {

	@Test
	public void retrieveProductBySku() throws Exception {

		ResultActions resultActions = mockMvc.perform(get("/products/" + TEST_PRODUCTS[0].getSku()));

		resultActions.andExpect(status().isOk()).andExpect(content().contentType(contentType));
		
		controllProduct(resultActions, TEST_PRODUCTS[0]);
	}

	@Test
	public void retrieveProductBySkuNotFound() throws Exception {

		ResultActions resultActions = mockMvc.perform(get("/products/" + ID_PRODUCT_NOT_FOUND))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new SkuNotFoundException(ID_PRODUCT_NOT_FOUND);
		}

	}

	@Test
	public void retrieveProducts() throws Exception {

		// At first check if the size ist valid
		ResultActions resultActions = mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(TEST_PRODUCTS.length)));

		// Now Check the Content
		for (int i = 0; i < TEST_PRODUCTS.length; i++) {
			resultActions.andExpect(jsonPath("$["+i+"].sku", is(TEST_PRODUCTS[i].getSku())))
			.andExpect(jsonPath("$["+i+"].name", is(TEST_PRODUCTS[i].getName())))
			.andExpect(jsonPath("$["+i+"].price", is(TEST_PRODUCTS[i].getPrice())))
			.andExpect(jsonPath("$["+i+"].available", is(TEST_PRODUCTS[i].isAvailable())))
			.andExpect(jsonPath("$["+i+"].eans", containsInAnyOrder(TEST_PRODUCTS[i].getEans().toArray(new String[0]))));
		}

	}
}
