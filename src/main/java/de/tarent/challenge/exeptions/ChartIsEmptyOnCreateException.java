package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.chart.Chart;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChartIsEmptyOnCreateException extends RuntimeException {

	private static final long serialVersionUID = 6384896749534287371L;

	public ChartIsEmptyOnCreateException(Chart chart) {
		super("chart can not be empty on creation " + chart.getName());
	}
}
