package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.chart.Chart;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartIsEmptyOnCreateException.MESSAGE)
public class ChartIsEmptyOnCreateException extends RuntimeException {

	public static final String MESSAGE = "chart can not be empty on creation";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;
	
	private static final long serialVersionUID = 6384896749534287371L;

	public ChartIsEmptyOnCreateException(Chart chart) {
		super("chart can not be empty on creation " + chart.getName());
	}
}
