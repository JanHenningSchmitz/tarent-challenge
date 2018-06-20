package de.tarent.challenge.store;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import de.tarent.challenge.store.chart.item.Chartitem;
import de.tarent.challenge.store.products.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	/**
	 * rawtypes, since its only for test purpose
	 */
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	private static final int SIZE_TEST_PRODUCT_ARRAY = 5;
	private static final Product[] TEST_PRODUCTS = new Product[SIZE_TEST_PRODUCT_ARRAY];
	private static final String ID_PRODUCT_NOT_FOUND = "666";

	/**
	 * This Method is invoked befor EVERY Test run!,
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {

		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		// Delete everything thats there
		mockMvc.perform(delete("/products/all"));

		// Specify the first for later testing
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("00000000"));
		TEST_PRODUCTS[0] = (new Product("test0", "test0", 2.5, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("11111111"));
		TEST_PRODUCTS[1] = (new Product("test1", "test1", 3.0, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("22222222"));
		TEST_PRODUCTS[2] = (new Product("test2", "test2", 2.7, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("33333333"));
		TEST_PRODUCTS[3] = (new Product("test3", "test3", 1.11, tmp_EANS));

		tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("44444444"));
		TEST_PRODUCTS[4] = (new Product("test4", "test4", 47.11, tmp_EANS));

		// Inserting the given test data
		for (int i = 0; i < TEST_PRODUCTS.length; i++) {

			this.mockMvc.perform(post("/products").contentType(contentType).content(json(TEST_PRODUCTS[i])))
					.andExpect(status().isCreated());
		}

	}

	@Test
	public void createNewChartAndAddItems() throws Exception {

		String testUserName = "TestUser";
		
		Product product = TEST_PRODUCTS[0];
		int quantity = 2;
		double price = product.getPrice();
		double expectetPrice = quantity * price;

		this.mockMvc.perform(post("/charts").contentType(contentType).content(testUserName))
				.andExpect(status().isCreated());

		this.mockMvc.perform(get("/charts/" + testUserName)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is(testUserName)))
				.andExpect(jsonPath("$.totalprice", is(0.0)))
				.andExpect(jsonPath("$.chartitems", containsInAnyOrder((new String[0]))));
		
		Chartitem item = new Chartitem(product.getSku(), 2);
		String json = json(item);
		
		this.mockMvc.perform(put("/charts/"+testUserName)
			.contentType(contentType).content(json)).andExpect(status().isOk());

		this.mockMvc.perform(get("/charts/" + testUserName)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is(testUserName)))
				.andExpect(jsonPath("$.totalprice", is(expectetPrice)));
				//.andExpect(jsonPath("$.eans", containsInAnyOrder(tmp_EANS.toArray(new String[0]))));

	}

	/**
	 * Test if its possible to insert, read and delete a new Product without an
	 * error
	 * 
	 * @throws Exception
	 */
	@Test
	public void addReadAndDeleteNewProduct() throws Exception {

		String sku = "4545";
		String name = "ShortLive";
		double price = 2.22;
		Set<String> tmp_EANS = new HashSet<String>();
		tmp_EANS.addAll(Arrays.asList("12344321", "77777777", "23498128"));

		Product tmp_Product = new Product(sku, name, price, tmp_EANS);
		String json = json(tmp_Product);

		this.mockMvc.perform(post("/products").contentType(contentType).content(json)).andExpect(status().isCreated());

		this.mockMvc.perform(get("/products/" + sku)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.sku", is(sku)))
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.eans", containsInAnyOrder(tmp_EANS.toArray(new String[0]))));

		this.mockMvc.perform(delete("/products/" + sku).contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void retrieveProducts() throws Exception {

		// At first check if the size ist valid
		ResultActions resultActions = mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(TEST_PRODUCTS.length)));

		// Now Check the Content
		for (int i = 0; i < TEST_PRODUCTS.length; i++) {
			resultActions.andExpect(jsonPath("$[" + i + "].sku", is(TEST_PRODUCTS[i].getSku())))
					.andExpect(jsonPath("$[" + i + "].name", is(TEST_PRODUCTS[i].getName())))
					.andExpect(jsonPath("$[" + i + "].eans",
							containsInAnyOrder(TEST_PRODUCTS[i].getEans().toArray(new String[0]))));
		}

	}

	@Test
	public void retrieveProductBySku() throws Exception {

		// First the Header
		mockMvc.perform(get("/products/" + TEST_PRODUCTS[0].getSku())).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.sku", is(TEST_PRODUCTS[0].getSku())))
				.andExpect(jsonPath("$.name", is(TEST_PRODUCTS[0].getName())))
				.andExpect(jsonPath("$.eans", containsInAnyOrder(TEST_PRODUCTS[0].getEans().toArray(new String[0]))));

	}

	@Test
	public void retrieveProductBySkuNotFound() throws Exception {

		mockMvc.perform(get("/products/" + ID_PRODUCT_NOT_FOUND)).andExpect(status().isNotFound());
	}

	/**
	 * Unchecked, since its only for test purpose
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Test
	public void contextLoads() {
	}

}
