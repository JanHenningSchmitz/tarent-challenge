package de.tarent.challenge.store.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import de.tarent.challenge.store.chart.Chart;
import de.tarent.challenge.store.chart.item.Chartitem;

@Entity
public class ChartForThymeleaf {

	@Id
	public String name;
	public double totalprice;
	public boolean checkedout;
	public String formattedDate;

	@ElementCollection
	public List<ChartItemForThymeleaf> items;

	public ChartForThymeleaf(Chart chart) {
		this.name = chart.getName();
		this.totalprice = chart.getTotalprice();
		this.checkedout = chart.isCheckedout();
		this.items = new ArrayList<ChartItemForThymeleaf>();

		Date checkOutDate = new Date(chart.getCheckoutdate());
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		formattedDate = formatter.format(checkOutDate);

		for (String chartitem : chart.getItems()) {
			items.add(new ChartItemForThymeleaf(chartitem));
		}
	}

	@Entity
	public static class ChartItemForThymeleaf {
		@Id
		public String sku;
		public int quantity;

		public ChartItemForThymeleaf(String chartitem) {
			this.sku = Chartitem.getSku(chartitem);
			this.quantity = Chartitem.getQuantity(chartitem);
		}
	}

}
