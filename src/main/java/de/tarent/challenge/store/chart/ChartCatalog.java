package de.tarent.challenge.store.chart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Catalog for alternative JPA calls on the Entity Chart
 * 
 * @author Jan-Henning Schmitz
 *
 */
public interface ChartCatalog extends JpaRepository<Chart, Long> {

	Optional<Chart> findByName(String name);
}
