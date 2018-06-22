package de.tarent.challenge.store.products;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Catalog for alternative JPA calls on the Entity Product
 * 
 * @author Jan-Henning Schmitz
 * @author tarent
 *
 */
public interface ProductCatalog extends JpaRepository<Product, Long> {

	Optional<Product> findBySku(String sku);

}
