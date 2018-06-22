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
import de.tarent.challenge.store.StoreApplicationTests;
import de.tarent.challenge.store.products.Product;

/**
 * Test class for product adding test cases
 * 
 * @author Jan-Henning Schmitz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAddTest extends StoreApplicationTests {

	@Before
	public void setup() throws IOException, Exception {
		super.setup(this.getClass().getSimpleName());
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

	/**
	 * Try to add a product thats already in the database and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Try to add with a invalid SKU and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * try to add without a SKU and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * try to create with invalid name and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * try to add without name and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * try to create with price = 0 and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * try to create with price < 0 and fail
	 * 
	 * @throws Exception
	 */
	@Test
	public void addPriceBelowZeroProduct() throws Exception {

		String sku = "notCreated";
		String name = "notCreated";
		double price = -5;
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

	/**
	 * Try to create with empty EAN list and fail
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Try to create new with invalid EANs and fail
	 * 
	 * @throws Exception
	 */
	@Test
	public void addInvalidEans() throws Exception {

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
