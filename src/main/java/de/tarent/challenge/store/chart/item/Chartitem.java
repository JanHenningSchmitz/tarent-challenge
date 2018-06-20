package de.tarent.challenge.store.chart.item;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.google.common.base.MoreObjects;

@Entity
@IdClass(Chartitem.class)
public class Chartitem implements Serializable {

	private static final long serialVersionUID = -3684297046665909369L;

	@Id
	private String sku;

	@Id
	private int quantity;

	@SuppressWarnings("unused") // For JPA
	private Chartitem() {
	}

	public Chartitem(String sku, int quantity) {
		this.sku = sku;
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public int getQuantity() {
		return quantity;
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
