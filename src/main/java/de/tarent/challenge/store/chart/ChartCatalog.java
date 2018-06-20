package de.tarent.challenge.store.chart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartCatalog extends JpaRepository<Chart, Long> {

	Optional<Chart> findByName(String name);
}
