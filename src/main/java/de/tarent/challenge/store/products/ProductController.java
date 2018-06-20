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

import de.tarent.challenge.exeptions.EanIsEmptyException;
import de.tarent.challenge.exeptions.ErrorWhileChangingException;
import de.tarent.challenge.exeptions.InvalidProductNameException;
import de.tarent.challenge.exeptions.InvalidSkuException;
import de.tarent.challenge.exeptions.NoEansException;
import de.tarent.challenge.exeptions.ProductAllreadyInUseException;
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
        return this.checkIfSkuIsInDB(sku);
    }
    
    /**
     * ## Task 1: Manage products
     * 	- Add the ability to create and update products.
     *  - Validate the product and enforce data integrity constraints:
     * 		- SKU: required, not empty, unique
     * 		- Name: required, not empty
     * 		- EANs: At least one, non-empty item
     * @param input
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody Product input)
    {    	

    	validateSkuData(input);
    	validateNameData(input);
    	validateEanData(input);
    	
    	Product result = this.productService.save(input);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(result.getSku()).toUri();

		return ResponseEntity.created(location).build();
    	
    }

    /**
     * EANs: At least one, non-empty item
     * @param input
     */
    private void validateEanData(Product input) {
    	// Name: required, not empty
    	if(input.getEans() == null || input.getEans().size() < 1) {
    		throw new NoEansException();
    	}   
    	
    	// Name: not empty
    	for(String ean : input.getEans()) {
    		if(ean == null || ean.trim().length() == 0)
    		throw new EanIsEmptyException();
    	}   
    }
    
    /**
     * Name: required, not empty
     * @param input
     */
    private void validateNameData(Product input) {
    	// Name: required, not empty
    	if(input.getName() == null || input.getName().trim().length() == 0) {
    		throw new InvalidProductNameException();
    	}   
    }
    
    /**
     * SKU: required, not empty, unique
     */
    private void validateSkuData(Product input) {
    	// SKU: required, not empty
    	if(input.getSku() == null || input.getSku().trim().length() == 0) {
    		throw new InvalidSkuException();
    	}   	
    	
    	// SKU: unique
    	try {
        	if(this.checkIfSkuIsInDB(input.getSku()) != null) {
        		throw new ProductAllreadyInUseException(input.getSku());
        	}
    	}catch(SkuNotFoundException snfe){
    		// Do nothing, since this exception is wanted in this case
    	}
    }
    
    @PutMapping("/{sku}")
    public Product changeProduct(@PathVariable String sku, @RequestBody Product input) {

    	// Validate and throw Error if not there
    	this.checkIfSkuIsInDB(sku);
    	
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
    	Product product = this.checkIfSkuIsInDB(sku);
    	
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
    private Product checkIfSkuIsInDB(String sku) {
    	return this.productService.retrieveProductBySku(sku).orElseThrow(
				() -> new SkuNotFoundException(sku));
    }
    
}
