package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class ProductControllerDeleteTests extends ProductControllerTests {

	@Test
	public void deleteAllProducts() throws Exception {

		this.mockMvc.perform(delete("/products/all"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProduct() throws Exception {

		this.mockMvc.perform(delete("/products/" + TEST_PRODUCTS[0].getSku()).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void addDeleteProductThatsNotThere() throws Exception {

		ResultActions resultActions = this.mockMvc.perform(delete("/products/" + "noSku").contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
