package de.tarent.challenge.store.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tarent.challenge.store.products.rest.ProductDelete;
import de.tarent.challenge.store.products.rest.ProductGet;
import de.tarent.challenge.store.products.rest.ProductPost;
import de.tarent.challenge.store.products.rest.ProductPut;
import de.tarent.challenge.store.products.rest.validation.ProductValidator;

/**
 * Rest controller for the URI /products
 * calls the product rest classes
 * @author Jan-Henning Schmitz
 * @author tarent
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductGet productGet;
	private final ProductPost productPost;
	private final ProductPut productPut;
	private final ProductDelete productDelete;
	private final ProductValidator productValidator;

	public ProductController(ProductService productService) {
		productGet = new ProductGet(productService);

		productValidator = new ProductValidator(productGet);

		productPost = new ProductPost(productService, productValidator);
		productPut = new ProductPut(productService, productValidator);
		productDelete = new ProductDelete(productService, productGet);
	}

	/**
	 * Get All Products
	 * 
	 * @return
	 */
	@GetMapping
	public Iterable<Product> retrieveProducts() {
		return productGet.getAll();
	}

	/**
	 * Get one Product by SKU
	 * 
	 * @param sku
	 * @return
	 */
	@GetMapping("/{sku}")
	public Product retrieveProductBySku(@PathVariable String sku) {
		return productGet.getBySku(sku);
	}

	/**
	 * ## Task 1: Manage products - Add the ability to create and update products. -
	 * Validate the product and enforce data integrity constraints: - SKU: required,
	 * not empty, unique - Name: required, not empty - EANs: At least one, non-empty
	 * item
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product input) throws Exception {
		return productPost.addProduct(input);
	}

	/**
	 * Changing the Product Name
	 * 
	 * @param sku
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/{sku}/name/{newName}")
	public Product changeProductName(@PathVariable String sku, @PathVariable String newName) throws Exception {
		return productPut.changeProductName(sku, newName);
	}

	/**
	 * Changing the Product Price
	 * 
	 * @param sku
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/{sku}/price/{newPrice:.+}")
	public Product changeProductPrice(@PathVariable String sku, @PathVariable double newPrice) throws Exception {
		return productPut.changeProductPrice(sku, newPrice);
	}

	/**
	 * Changing the Product Available State
	 * 
	 * @param sku
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/{sku}/available/{newAvailable}")
	public Product changeProductAvailable(@PathVariable String sku, @PathVariable boolean newAvailable) throws Exception {
		return productPut.changeProductAvailable(sku, newAvailable);
	}

	/**
	 * Deleting a EAN from the product
	 * 
	 * @param sku
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "/{sku}/eans/{deleteEan}")
	public Product removeEanFromProduct(@PathVariable String sku, @PathVariable String deleteEan) throws Exception {
		return productPut.removeEanFromProduct(sku, deleteEan);
	}

	/**
	 * Adding EAN to the product
	 * 
	 * @param sku
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/{sku}/eans/{addEan}")
	public Product addEanToProduct(@PathVariable String sku, @PathVariable String addEan) throws Exception {
		return productPut.addEanToProduct(sku, addEan);
	}

	/**
	 * Deleting a Product by SKU
	 */
	@DeleteMapping("/{sku}")
	public void deleteBySKU(@PathVariable String sku) {
		productDelete.deleteBySKU(sku);
	}

	/**
	 * Deleting all Products, JUST FOR TESTING
	 */
	@DeleteMapping("/all")
	public void deleteAll() {
		productDelete.deleteAll();

	}

}
