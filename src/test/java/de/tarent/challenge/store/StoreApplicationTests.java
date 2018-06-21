package de.tarent.challenge.store;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.products.Product;

public class StoreApplicationTests {

	protected MockMvc mockMvc;

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	/**
	 * rawtypes, since its only for test purpose
	 */
	@SuppressWarnings("rawtypes")
	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	protected static final int SIZE_TEST_PRODUCT_ARRAY = 5;
	protected static final Product[] TEST_PRODUCTS = new Product[SIZE_TEST_PRODUCT_ARRAY];
	protected static final String ID_PRODUCT_NOT_FOUND = "666";
	protected static final int SIZE_TEST_CHART_ARRAY = 4;
	protected static Chart[] TESTCHARTS = new Chart[SIZE_TEST_CHART_ARRAY];

	/**
	 * This Method is invoked before EVERY Test run!,
	 * 
	 * @throws Exception
	 */
	public void setupProducts() throws Exception {

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

}
