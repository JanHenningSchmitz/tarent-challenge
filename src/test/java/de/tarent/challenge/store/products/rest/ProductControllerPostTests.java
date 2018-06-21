package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.InvalidProductNameException;
import de.tarent.challenge.exeptions.InvalidSkuException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerPostTests extends ProductControllerTests {

	/**
	 * Test if its possible to insert, read and delete a new Product without an
	 * error
	 *
	 * @throws Exception
	 */
	@Test
	public void addNewProduct() throws Exception {

		String sku = "4545";
		String name = "ShortLive";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("12344321", "77777777", "23498128"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		this.mockMvc.perform(post("/products").contentType(contentType).content(json)).andExpect(status().isCreated());

	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void addDublicateProduct() throws Exception {

		Product tmp_Product = TEST_PRODUCTS[0];
		String json = json(tmp_Product);

		this.mockMvc.perform(post("/products").contentType(contentType).content(json));

	}

	@Test
	public void addInvalidSkuProduct() throws Exception {
		String sku = "   ";
		String name = "notCreated";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("notCreated"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(InvalidSkuException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidSkuException.MESSAGE.equals(errorMsg)) {
			throw new InvalidSkuException();
		}

	}

	@Test
	public void addNullSkuProduct() throws Exception {
		String sku = null;
		String name = "notCreated";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("notCreated"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(InvalidSkuException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidSkuException.MESSAGE.equals(errorMsg)) {
			throw new InvalidSkuException();
		}
	}

	@Test
	public void addInvalidNameProduct() throws Exception {
		String sku = "notCreated";
		String name = "    ";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("notCreated"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(InvalidProductNameException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidProductNameException.MESSAGE.equals(errorMsg)) {
			throw new InvalidProductNameException();
		}
	}

	@Test
	public void addNullNameProduct() throws Exception {
		String sku = "notCreated";
		String name = null;
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("notCreated"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(InvalidProductNameException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!InvalidProductNameException.MESSAGE.equals(errorMsg)) {
			throw new InvalidProductNameException();
		}
	}

	@Test
	public void addInvalidEansProduct() throws Exception {

	}
}
