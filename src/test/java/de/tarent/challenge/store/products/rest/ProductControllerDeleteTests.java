package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerDeleteTests extends ProductControllerTests {

	@Test
	public void deleteProduct() throws Exception {

		this.mockMvc.perform(delete("/products/" + TEST_PRODUCTS[0]).contentType(contentType))
				.andExpect(status().isOk());
	}

	// @Test
	// public void addDeleteProductThatsNotThere() throws Exception {
	//
	// ResultActions resultActions = this.mockMvc.perform(delete("/products/" +
	// "noSku").contentType(contentType))
	// .andExpect(status().is(SkuNotFoundException.STATUS.value()));
	//
	// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
	// if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
	// throw new SkuNotFoundException(ID_PRODUCT_NOT_FOUND);
	// }
	// }
	//
	// @Test
	// public void addDeleteProductWithNull() throws Exception {
	//
	// ResultActions resultActions =
	// this.mockMvc.perform(delete("/products/").contentType(contentType))
	// .andExpect(status().is(SkuNotFoundException.STATUS.value()));
	//
	// String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
	// if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
	// throw new SkuNotFoundException(ID_PRODUCT_NOT_FOUND);
	// }
	// }

}
