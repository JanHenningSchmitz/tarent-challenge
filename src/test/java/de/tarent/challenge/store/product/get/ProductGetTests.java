package de.tarent.challenge.store.product.get;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.sku.SkuNotFoundException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductGetTests extends ProductControllerTests {

	protected Product testproduct2 = null;

	@Before
	public void setup() throws IOException, Exception {

		super.setup(this.getClass().getSimpleName());

		// Creating testdata
		Set<String> test_product2_eans = new HashSet<String>();
		test_product2_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct2 = new Product(this.getClass().getSimpleName() + 2, this.getClass().getSimpleName() + 2, 2.1, true,
				test_product2_eans);
		createTestProduct(testproduct2);
	}

	@Test
	public void retrieveProductBySku() throws Exception {

		String uri = "/products/" + testproduct.getSku();

		ResultActions resultActions = mockMvc.perform(get(uri));

		try {
			resultActions.andExpect(status().isOk()).andExpect(content().contentType(contentType));
			controllProduct(resultActions, testproduct);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Test
	public void retrieveProductBySkuNotFound() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "NotFound";

		ResultActions resultActions = mockMvc.perform(get(uri))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

	}

	@Test
	public void retrieveProducts() throws Exception {

		Product[] products = new Product[] { testproduct, testproduct2 };

		String uri = "/products";

		// At first check if the size ist valid
		ResultActions resultActions = mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(products.length)));

		// Now Check the Content
		for (int i = 0; i < products.length; i++) {
			resultActions.andExpect(jsonPath("$[" + i + "].sku", is(products[i].getSku())))
					.andExpect(jsonPath("$[" + i + "].name", is(products[i].getName())))
					.andExpect(jsonPath("$[" + i + "].price", is(products[i].getPrice())))
					.andExpect(jsonPath("$[" + i + "].available", is(products[i].isAvailable()))).andExpect(jsonPath(
							"$[" + i + "].eans", containsInAnyOrder(products[i].getEans().toArray(new String[0]))));
		}

	}
}
