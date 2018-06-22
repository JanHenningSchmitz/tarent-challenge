package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartNotFoundException.MESSAGE)
public class ChartNotFoundException extends RuntimeException {

	public static final String MESSAGE = "could not find chart";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = 2058279314171262383L;

	public ChartNotFoundException(String name) {
		super("could not find chart '" + name + "'.");
	}
}
