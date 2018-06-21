package de.tarent.challenge.store.product.eans;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.SkuNotFoundException;
import de.tarent.challenge.exeptions.product.ean.CantDeleteLastEan;
import de.tarent.challenge.exeptions.product.ean.EanAllreadyOnProductException;
import de.tarent.challenge.exeptions.product.ean.EanNotOnProductException;
import de.tarent.challenge.store.products.ProductControllerTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductEansTests extends ProductControllerTests {

	@Before
	public void setup() throws IOException, Exception {
		
		super.setup(this.getClass().getSimpleName());
	}

	@Test
	public void addEan() throws Exception {

		testproduct.getEans().add("newEan");

		String uri = "/products/" + testproduct.getSku() + "/eans/" + "newEan";

		ResultActions resultActions = this.mockMvc.perform(post(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}

	@Test
	public void addEanThatIsAllreadyThere() throws Exception {

		testproduct.getEans().add("ean1");

		String uri = "/products/" + testproduct.getSku() + "/eans/" + "ean1";

		ResultActions resultActions = this.mockMvc.perform(post(uri).contentType(contentType))
				.andExpect(status().is(EanAllreadyOnProductException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!EanAllreadyOnProductException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void deleteEan() throws Exception {

		testproduct.getEans().remove("ean1");

		String uri = "/products/" + testproduct.getSku() + "/eans/" + "ean1";

		ResultActions resultActions = this.mockMvc.perform(delete(uri).contentType(contentType))
				.andExpect(status().isOk());

		controllProduct(resultActions, testproduct);
	}

	@Test
	public void deleteLastEan() throws Exception {

		deleteEan();

		testproduct.getEans().remove("ean2");

		String uri = "/products/" + testproduct.getSku() + "/eans/" + "ean2";

		ResultActions resultActions = this.mockMvc.perform(delete(uri).contentType(contentType))
				.andExpect(status().is(CantDeleteLastEan.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!CantDeleteLastEan.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void deleteEanThatIsNotThere() throws Exception {

		testproduct.getEans().remove("eanNotThere");

		String uri = "/products/" + testproduct.getSku() + "/eans/" + "eanNotThere";

		ResultActions resultActions = this.mockMvc.perform(delete(uri).contentType(contentType))
				.andExpect(status().is(EanNotOnProductException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!EanNotOnProductException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

	@Test
	public void changeNameInvalidSku() throws Exception {

		String uri = "/products/" + testproduct.getSku() + "invalid" + "/name/" + "newName";

		ResultActions resultActions = this.mockMvc.perform(put(uri).contentType(contentType))
				.andExpect(status().is(SkuNotFoundException.STATUS.value()));

		String errorMsg = resultActions.andReturn().getResponse().getErrorMessage();
		if (!SkuNotFoundException.MESSAGE.equals(errorMsg)) {
			throw new Exception(errorMsg);
		}
	}

}
