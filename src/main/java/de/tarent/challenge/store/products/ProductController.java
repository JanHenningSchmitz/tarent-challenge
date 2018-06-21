package de.tarent.challenge.store.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.tarent.challenge.store.products.rest.ProductDelete;
import de.tarent.challenge.store.products.rest.ProductGet;
import de.tarent.challenge.store.products.rest.ProductPost;
import de.tarent.challenge.store.products.rest.ProductPut;
import de.tarent.challenge.store.products.rest.validation.ProductValidator;

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
	 * Get one Product by sku
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
	 */
	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product input) {
		return productPost.addProduct(input);
	}

	@RequestMapping(value = "/{sku}", method = RequestMethod.PUT)
	public Product changeProduct(@PathVariable String sku, @RequestBody Product input) {
		return productPut.changeProduct(sku, input);
	}

	/**
	 * Deleting a Product by sku
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
