package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartAllreadyInUseException.MESSAGE)
public class ChartAllreadyInUseException extends RuntimeException {
	private static final long serialVersionUID = 2832500451560612446L;

	public static final String MESSAGE = "chart name allready in use.";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public ChartAllreadyInUseException(String name) {
		super("chart name '" + name + "' allready in use.");
	}
}
