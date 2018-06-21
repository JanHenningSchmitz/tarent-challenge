package de.tarent.challenge.store;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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

	// @Test
	// public void createNewChartAndAddItems() throws Exception {
	//
	// double expectetPrice = 2 * TEST_PRODUCTS[0].getPrice();
	// List<Chartitem> chartItems = new ArrayList<Chartitem>();
	// chartItems.add(new Chartitem(TEST_PRODUCTS[0].getSku(), 2));
	//
	// String testUserName = "TestUser";
	// Chart testChart = new Chart(testUserName, chartItems, expectetPrice);
	//
	// this.mockMvc.perform(post("/charts").contentType(contentType).content(json(testChart)))
	// .andExpect(status().isCreated());
	//
	// this.mockMvc.perform(get("/charts/" +
	// testChart.getName())).andExpect(status().isOk())
	// .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name",
	// is(testChart.getName())))
	// .andExpect(jsonPath("$.totalprice", is(expectetPrice)));
	// // TODO Numbers of Elements should also be checked
	// // .andExpect(jsonPath("$.chartitems", containsInAnyOrder((new String[0]))));
	//
	// expectetPrice += 2 * TEST_PRODUCTS[1].getPrice();
	// addItemsToChart(TEST_PRODUCTS[1], testChart, 2);
	// readChart(testChart.getName(), expectetPrice);
	//
	// expectetPrice += TEST_PRODUCTS[2].getPrice() * 5;
	// addItemsToChart(TEST_PRODUCTS[2], testChart, 5);
	// readChart(testChart.getName(), expectetPrice);
	// }
	//
	// private void readChart(String name, double expectetPrice) throws Exception {
	// this.mockMvc.perform(get("/charts/" + name)).andExpect(status().isOk())
	// .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name",
	// is(name)))
	// .andExpect(jsonPath("$.totalprice", is(expectetPrice)));
	// // TODO Numbers of Elements should also be checked
	// // .andExpect(jsonPath("$.eans", containsInAnyOrder(tmp_EANS.toArray(new
	// // String[0]))));
	// }
	//
	// private void addItemsToChart(Product product, Chart chart, int quantity)
	// throws Exception {
	//
	// Chartitem item = new Chartitem(product.getSku(), quantity);
	// String json = json(item);
	//
	// this.mockMvc.perform(put("/charts/" +
	// chart.getName()).contentType(contentType).content(json))
	// .andExpect(status().isOk());
	// }
	//
	// /**
	// * Test if its possible to insert, read and delete a new Product without an
	// * error
	// *
	// * @throws Exception
	// */
	// @Test
	// public void addReadAndDeleteNewProduct() throws Exception {
	//
	// String sku = "4545";
	// String name = "ShortLive";
	// double price = 2.22;
	// Set<String> tmp_EANS = new HashSet<String>();
	// tmp_EANS.addAll(Arrays.asList("12344321", "77777777", "23498128"));
	//
	// Product tmp_Product = new Product(sku, name, price, true, tmp_EANS);
	// String json = json(tmp_Product);
	//
	// this.mockMvc.perform(post("/products").contentType(contentType).content(json)).andExpect(status().isCreated());
	//
	// this.mockMvc.perform(get("/products/" + sku)).andExpect(status().isOk())
	// .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.sku",
	// is(sku)))
	// .andExpect(jsonPath("$.name", is(name)))
	// .andExpect(jsonPath("$.eans", containsInAnyOrder(tmp_EANS.toArray(new
	// String[0]))));
	//
	// this.mockMvc.perform(delete("/products/" +
	// sku).contentType(contentType)).andExpect(status().isOk());
	// }
	//

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
