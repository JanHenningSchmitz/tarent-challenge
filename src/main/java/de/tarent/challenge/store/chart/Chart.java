package de.tarent.challenge.store.chart;

import static javax.persistence.GenerationType.AUTO;

import java.util.List;
import java.util.Objects;

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

	// TODO: Add Validation to create chart
	@Column(unique = true)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "chart")
	private List<Chartitem> chartitems;

	private double totalprice;
	
	private boolean checkedOut;

	@SuppressWarnings("unused") // For JPA
	private Chart() {
	}

	public Chart(String name, List<Chartitem> chartitems, double totalprice) {
		this.name = name;
		this.chartitems = chartitems;
		this.totalprice = totalprice;
		this.checkedOut = false;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}
	
	/**
	 * once checked out, it can never return
	 */
	public void setCheckedOut() {
		this.checkedOut = true;
	}
	
	public String getName() {
		return this.name;
	}

	public List<Chartitem> getChartitems() {
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
