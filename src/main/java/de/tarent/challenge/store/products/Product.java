package de.tarent.challenge.store.products;

import java.util.Objects;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;

/**
 * Entity Bean Product with Database Definitions
 * 
 * @author Jan-Henning Schmitz
 * @author tarent
 *
 */
@Entity
public class Product {

	@Id
	private String sku;

	private String name;

	private boolean available;

	/*
	 * ## Task 2: Prices for products - Extend the `Product` class with information
	 * about its price.
	 */
	private double price;

	@ElementCollection
	private Set<String> eans;

	@SuppressWarnings("unused") // For JPA
	private Product() {
	}

	public Product(String sku, String name, double price, boolean available, Set<String> eans) {
		this.sku = sku;
		this.name = name;
		this.eans = eans;
		this.price = price;
		this.available = available;
	}

	@SuppressWarnings("unused") // To hide from altering
	private final void setSku(String sku) {
		this.sku = sku;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getSku() {
		return sku;
	}

	public String getName() {
		return name;
	}

	public Set<String> getEans() {
		return eans;
	}

	public double getPrice() {
		return this.price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product) o;
		return Objects.equals(sku, product.sku) && Objects.equals(name, product.name)
				&& Objects.equals(eans, product.eans) && Objects.equals(price, product.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sku, name, eans, price);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("sku", sku).add("name", name).add("eans", eans).add("price", price)
				.toString();
	}
}
