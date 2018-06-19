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

	// public void insertProduct(Product product) {
	// productCatalog.insertProduct(product);
	// }
}
