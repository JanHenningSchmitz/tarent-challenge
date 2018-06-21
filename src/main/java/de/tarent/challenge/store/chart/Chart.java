package de.tarent.challenge.store.chart;

import static javax.persistence.GenerationType.AUTO;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import de.tarent.challenge.store.chart.item.Chartitem;

@Entity
public class Chart {

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@Column(unique = true)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "chart", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Chartitem> chartitems;

	private double totalprice;

	private boolean checkedout;

	@SuppressWarnings("unused") // For JPA
	private Chart() {
	}

	public Chart(String name, Set<Chartitem> chartitems, double totalprice) {
		this.name = name;
		this.chartitems = chartitems;
		this.totalprice = totalprice;
		this.checkedout = false;
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

	public Set<Chartitem> getChartitems() {
		return chartitems;
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
		return Objects.equals(id, chart.id) && Objects.equals(name, chart.name)
				&& Objects.equals(chartitems, chart.chartitems) && Objects.equals(totalprice, chart.totalprice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, chartitems, totalprice);
	}

	@Override
	public String toString() {

		return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("chartItems", chartitems)
				.add("totalPrice", totalprice).toString();
	}
}
