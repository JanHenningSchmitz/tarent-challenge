package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartIsNullException.MESSAGE)
public class ChartIsNullException extends RuntimeException {

	public static final String MESSAGE = "chart should not be null";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -5021249292317420552L;

	public ChartIsNullException() {
		super("chart should not be null");
	}
}
