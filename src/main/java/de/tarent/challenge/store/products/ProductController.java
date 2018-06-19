package de.tarent.challenge.store.products;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tarent.challenge.exeptions.SkuNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Iterable<Product> retrieveProducts() {
        return productService.retrieveAllProducts();
    }

    @GetMapping("/{sku}")
    public Product retrieveProductBySku(@PathVariable String sku) {
    	
    	// No need for extra validation, sice its allready there
    	this.validateSku(sku);
    	
        return this.productService.retrieveProductBySku(sku).orElseThrow(
				() -> new SkuNotFoundException(sku));
    }
    
    @PostMapping(path="/put/{id},{name}")
    public void testProductInsert(@PathVariable Long id, @PathVariable String name)
    {
    	System.out.println("Test: " + id + "," + name);
    }
    
    /**
     * Validation the SKU, needs to be before altering and deleting
     * @param sku
     */
    private void validateSku(String sku) {
    	this.productService.retrieveProductBySku(sku).orElseThrow(
				() -> new SkuNotFoundException(sku));
    }
    
    /*@PostMapping("/post/{id},{name},{sku}")
    public void insertProductByValues(@PathVariable Long id, @PathVariable String name, @PathVariable String sku) {
    	Product product = null;
    	System.out.println(id + "," + name + "," + sku);
    	productService.insertProduct(product);
    }*/
    
}
