package de.tarent.challenge.store.product.add;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.product.ean.EanIsEmptyException;
import de.tarent.challenge.exeptions.product.ean.NoEansException;
import de.tarent.challenge.exeptions.product.name.InvalidProductNameException;
import de.tarent.challenge.exeptions.product.price.PriceLowerZeroException;
import de.tarent.challenge.exeptions.product.sku.InvalidSkuException;
import de.tarent.challenge.exeptions.product.sku.ProductAllreadyInUseException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAddTest extends ProductControllerTests {

	private Product testproduct = null;
	
	@Before
	public void setup() throws IOException, Exception {

		super.setup();

		// Creating testdata
		Set<String> test_product_eans = new HashSet<String>();
		test_product_eans.addAll(Arrays.asList("00000000", "00000001"));
		testproduct = new Product("ProductAddTest", "ProductAddTest", 2.1, true, test_product_eans);
		createTestProduct(testproduct);
	}
	
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

	@Test
	public void addDublicateProduct() throws Exception {

		Product tmp_Product = testproduct;
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(ProductAllreadyInUseException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!ProductAllreadyInUseException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}

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
			throw new Exception(errorMsg);
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
			throw new Exception(errorMsg);
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
			throw new Exception(errorMsg);
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
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void addPriceZeroProduct() throws Exception {

		String sku = "notCreated";
		String name = "notCreated";
		double price = 0;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("notCreated"));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(PriceLowerZeroException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!PriceLowerZeroException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void addEmptyListEansProduct() throws Exception {
		String sku = "notCreated";
		String name = "notCreated";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(new ArrayList<String>());

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(NoEansException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!NoEansException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void addEmptyEans() throws Exception {

		String sku = "notCreated";
		String name = "notCreated";
		double price = 0;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("   "));

		Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
		String json = json(tmp_Product);

		ResultActions resultActions = this.mockMvc.perform(post("/products").contentType(contentType).content(json))
				.andExpect(status().is(EanIsEmptyException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!EanIsEmptyException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}
}
