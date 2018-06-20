package de.tarent.challenge.store.products;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private final ProductCatalog productCatalog;

	public ProductService(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	public List<Product> retrieveAllProducts() {
		return productCatalog.findAll();
	}

	public Optional<Product> retrieveProductBySku(String sku) {
		return productCatalog.findBySku(sku);
	}

	public Product save(Product product) {
		return productCatalog.saveAndFlush(product);
	}
	
	public void delete(Product product) {
		productCatalog.delete(product);
	}	
	
	/**
	 * JUST FOR TESTING!
	 * TODO: Anyway to keep this out of the Production Build?
	 */
	public void deleteAll() {
		productCatalog.deleteAll();
	}
	
	public Product change(Product product) {
		return productCatalog.saveAndFlush(product);
	}
}
