package de.tarent.challenge.store.chart;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

/**
 * Entity Bean Chart with Database Definitions
 * 
 * @author Jan-Henning Schmitz
 *
 */
@Entity
public class Chart {

	@Id
	@Column(unique = true)
	@NotNull
	private String name;

	// See: Chart item
	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "chart", cascade = {
	// CascadeType.MERGE,
	// CascadeType.REFRESH }, orphanRemoval = true)
	// private Set<Chartitem> chartitems;

	/**
	 * String made of <sku>$<quantity>
	 */
	@ElementCollection
	private Set<String> items;

	private double totalprice;

	private boolean checkedout;

	@SuppressWarnings("unused") // For JPA
	private Chart() {
	}

	public Chart(String name, Set<String> items, double totalprice) {
		this.name = name;
		this.items = items;
		this.totalprice = totalprice;
		this.checkedout = false;
	}
	
	public Chart(String name, Set<String> items, double totalprice, boolean checkedout) {
		this(name, items, totalprice);
		this.checkedout = checkedout;
	}

	public boolean isCheckedout() {
		return checkedout;
	}

	/**
	 * once checked out, it can never return
	 */
	public void setCheckedout() {
		this.checkedout = true;
	}

	public String getName() {
		return this.name;
	}

	public Set<String> getItems() {
		return items;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Chart chart = (Chart) o;
		return Objects.equals(name, chart.name)
				&& Objects.equals(items, chart.items) && Objects.equals(totalprice, chart.totalprice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, items, totalprice);
	}

	@Override
	public String toString() {

		return MoreObjects.toStringHelper(this).add("name", name).add("items", items)
				.add("totalPrice", totalprice).toString();
	}
}
