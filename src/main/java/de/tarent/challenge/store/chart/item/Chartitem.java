package de.tarent.challenge.store.chart.item;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import de.tarent.challenge.store.chart.Chart;

@Entity
public class Chartitem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String sku;

	private int quantity;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "chart_id")
	private Chart chart;

	@SuppressWarnings("unused") // For JPA
	private Chartitem() {
	}

	public Chartitem(String sku, int quantity) {
		this.sku = sku;
		this.quantity = quantity;
	}

	public Chart getChart() {
		return this.chart;
	}

	public String getSku() {
		return sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public int addQuantity(int add) {
		return quantity + add;
	}

	public int subQuantity(int sub) {
		return quantity - sub;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Chartitem chartItem = (Chartitem) o;
		return Objects.equals(sku, chartItem.sku) && Objects.equals(quantity, chartItem.quantity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sku, quantity);
	}

	@Override
	public String toString() {

		return MoreObjects.toStringHelper(this).add("sku", sku).add("quantity", quantity).toString();
	}
}
