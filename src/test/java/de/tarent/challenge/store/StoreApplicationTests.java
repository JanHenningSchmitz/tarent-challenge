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
import de.tarent.challenge.store.chart.item.Chartitem;
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

	protected Product testproduct = null;
	protected Chart testchart = null;

	public void setup(String testproductname) throws Exception {

		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		// Delete everything thats there to set up a new Test DB
		this.mockMvc.perform(delete("/products/all"));
		this.mockMvc.perform(delete("/charts/all"));

		Set<String> test_product_eans = new HashSet<String>();
		test_product_eans.addAll(Arrays.asList("ean1", "ean2"));
		testproduct = new Product(testproductname, testproductname, 2.1, true, test_product_eans);
		createTestProduct(testproduct);

		Set<String> tmp_ChartItems = new HashSet<String>();
		tmp_ChartItems.add(Chartitem.createChartitem(this.testproduct.getSku(), 1));

		testchart = new Chart("testchart", tmp_ChartItems, (this.testproduct.getPrice()));
		createTestChart(testchart);
	}

	protected void createTestProduct(Product product) throws IOException, Exception {

		this.mockMvc.perform(post("/products").contentType(contentType).content(json(product)))
				.andExpect(status().isCreated());
	}

	protected void createTestChart(Chart chart) throws IOException, Exception {

		this.mockMvc.perform(post("/charts").contentType(contentType).content(json(chart)))
				.andExpect(status().isCreated());
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
