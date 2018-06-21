package de.tarent.challenge.store.chart.item;

public class Chartitem {
	
	private static final String SPLITTER = ";";
	
	public static String createChartitem(String sku, int quantity) {
		return sku + SPLITTER + quantity;
	}
	
	public static final String getSku(String chartitem) {
		return chartitem.split(SPLITTER)[0];
	}
	
	public static final int getQuantity(String chartitem) {
		
		String[] split = chartitem.split(SPLITTER);
		
		return Integer.valueOf(split[1]);
	}
	
	public static final String addQuantity(String chartitem, int quantity) {
		int q = getQuantity(chartitem) + quantity;
		return getSku(chartitem) + SPLITTER + q;
	}
	
	public static final String subQuantity(String chartitem, int quantity) {
		int q = getQuantity(chartitem) - quantity;
		return getSku(chartitem) + SPLITTER + q;
	}

	// @Id
	// private String sku;
	//
	// private int quantity;
	//
	// @JsonIgnore
	// @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE,
	// CascadeType.REFRESH })
	// @JoinColumn(name = "chart_id")
	// private Chart chart;
	//
	// @SuppressWarnings("unused") // For JPA
	// private Chartitem() {
	// }
	//
	// public Chartitem(String sku, int quantity) {
	// this.sku = sku;
	// this.quantity = quantity;
	// }
	//
	// public Chart getChart() {
	// return this.chart;
	// }
	//
	// public String getSku() {
	// return sku;
	// }
	//
	// public int getQuantity() {
	// return quantity;
	// }
	//
	// public void setChart(Chart chart) {
	// this.chart = chart;
	// }
	//
	// public int addQuantity(int add) {
	// return quantity + add;
	// }
	//
	// public int subQuantity(int sub) {
	// return quantity - sub;
	// }
	//
	// @Override
	// public boolean equals(Object o) {
	// if (this == o)
	// return true;
	// if (o == null || getClass() != o.getClass())
	// return false;
	// Chartitem chartItem = (Chartitem) o;
	// return Objects.equals(sku, chartItem.sku) && Objects.equals(quantity,
	// chartItem.quantity);
	// }
	//
	// @Override
	// public int hashCode() {
	// return Objects.hash(sku, quantity);
	// }
	//
	// @Override
	// public String toString() {
	//
	// return MoreObjects.toStringHelper(this).add("sku", sku).add("quantity",
	// quantity).toString();
	// }
}
