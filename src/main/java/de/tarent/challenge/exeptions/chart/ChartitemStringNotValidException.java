package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartitemStringNotValidException.MESSAGE)
public class ChartitemStringNotValidException extends RuntimeException {

	private static final long serialVersionUID = -3271624303737273421L;

	public static final String MESSAGE = "chartitemstring invalid";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public ChartitemStringNotValidException() {
		super(MESSAGE);
	}
}
