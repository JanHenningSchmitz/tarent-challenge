package de.tarent.challenge.store.products;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tarent.challenge.exeptions.ErrorWhileChangingException;
import de.tarent.challenge.exeptions.SkuNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get All Products
     * @return
     */
    @GetMapping
    public Iterable<Product> retrieveProducts() {
        return productService.retrieveAllProducts();
    }

    /**
     * Get one Product by sku
     * @param sku
     * @return
     */
    @GetMapping("/{sku}")
    public Product retrieveProductBySku(@PathVariable String sku) {

    	// Validate and throw Error if not there
        return this.validateSku(sku);
    }
    
    /**
     * Insert one Product
     * @param input
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody Product input)
    {    	
    	Product result = this.productService.save(input);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(result.getSku()).toUri();

		return ResponseEntity.created(location).build();
    	
    }
    
    @PutMapping("/{sku}")
    public Product changeProduct(@PathVariable String sku, @RequestBody Product input) {

    	// Validate and throw Error if not there
    	this.validateSku(sku);
    	
    	try {
    		return this.productService.change(input);
        }catch(Exception e){
            throw new ErrorWhileChangingException(sku);
        }

    }
    
    /**
     * Deleting a Product by sku
     */
    @DeleteMapping("/{sku}")
    public void deleteBySKU(@PathVariable String sku) {

    	// Validate and throw Error if not there
    	Product product = this.validateSku(sku);
    	
    	try {
    		this.productService.delete(product);
    	}catch(IllegalArgumentException iae){
    		throw new SkuNotFoundException(sku);
    	}

    }
    
    /**
     * Deleting all Products, JUST FOR TESTING
     */
    @DeleteMapping("/all")
    public void deleteAll() {
    	this.productService.deleteAll();

    }
    
    /**
     * Validation the SKU, needs to be before altering and deleting
     * @param sku
     */
    private Product validateSku(String sku) {
    	return this.productService.retrieveProductBySku(sku).orElseThrow(
				() -> new SkuNotFoundException(sku));
    }
    
}
