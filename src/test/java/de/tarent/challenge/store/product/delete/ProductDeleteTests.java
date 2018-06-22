package de.tarent.challenge.store.product.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDeleteTests extends ProductControllerTests {

	@Before
	public void setup() throws IOException, Exception {
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void deleteAllProducts() throws Exception {

		this.mockMvc.perform(delete("/products/all")).andExpect(status().isOk());
	}

	@Test
	public void deleteProduct() throws Exception {

		this.mockMvc.perform(delete("/products/" + testproduct.getSku()).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void addDeleteProductThatsNotThere() throws Exception {

		ResultActions resultActions = this.mockMvc
				.perform(delete("/products/" + testproduct.getSku() + "notThere").contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
