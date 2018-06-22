package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.chart.Chart;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartWithWrongTotalPriceException.MESSAGE)
public class ChartWithWrongTotalPriceException extends RuntimeException {

	public static final String MESSAGE = "chart has a wrong total price ";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -7169312003055931672L;

	public ChartWithWrongTotalPriceException(Chart chart, double expected) {
		super("chart " + chart.getName() + " has a wrong total price " + chart.getTotalprice() + " expected "
				+ expected);
	}
}
