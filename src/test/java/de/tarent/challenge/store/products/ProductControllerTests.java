package de.tarent.challenge.store.products;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.store.StoreApplicationTests;

public class ProductControllerTests extends StoreApplicationTests {

	protected static final int SIZE_TEST_PRODUCT_ARRAY = 5;
	protected static final Product[] TEST_PRODUCTS = new Product[SIZE_TEST_PRODUCT_ARRAY];
	protected static final String ID_PRODUCT_NOT_FOUND = "666";

	/**
	 * This Method is invoked before EVERY Test run!,
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {

		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		// Delete everything thats there to set up a new Test DB
		mockMvc.perform(delete("/products/all"));


		// Specify the first for later testing
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("00000000", "00000001"));
		TEST_PRODUCTS[0] = (new Product("test0", "test0", 2.5, true, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("11111111"));
		TEST_PRODUCTS[1] = (new Product("test1", "test1", 3.0, true, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("22222222"));
		TEST_PRODUCTS[2] = (new Product("test2", "test2", 2.7, true, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("33333333"));
		TEST_PRODUCTS[3] = (new Product("test3", "test3", 1.11, true, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("44444444"));
		TEST_PRODUCTS[4] = (new Product("test4", "test4", 47.11, false, tmp_EANS));

		// Inserting the given test data
		for (int i = 0; i < TEST_PRODUCTS.length; i++) {

			this.mockMvc.perform(post("/products").contentType(contentType).content(json(TEST_PRODUCTS[i])))
					.andExpect(status().isCreated());
		}

	}
		
	protected void controllProduct(ResultActions resultActions, Product product) throws Exception {
		resultActions.andExpect(jsonPath("$.sku", is(product.getSku())))
		.andExpect(jsonPath("$.name", is(product.getName())))
		.andExpect(jsonPath("$.price", is(product.getPrice())))
		.andExpect(jsonPath("$.available", is(product.isAvailable())))
		.andExpect(jsonPath("$.eans", containsInAnyOrder(product.getEans().toArray(new String[0]))));
	}

}
