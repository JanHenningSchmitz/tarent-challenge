package de.tarent.challenge.store.products;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductCatalog extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
    
//    void insertProduct(Product product);

}
