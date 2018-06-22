package de.tarent.challenge.store.products;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Service class for the Product Entity, calls the catalog
 * @author Jan-Henning Schmitz
 * @author tarent
 *
 */
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

	public Product change(Product product) {
		return productCatalog.saveAndFlush(product);
	}

	public void delete(Product product) {
		productCatalog.delete(product);
		productCatalog.flush();
	}

	public void deleteAll() {
		productCatalog.deleteAll();
		productCatalog.flush();
	}
}
