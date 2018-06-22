package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartNameInvalidException.MESSAGE)
public class ChartNameInvalidException extends RuntimeException {

	public static final String MESSAGE = "name must not be blank";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -3524594641354284164L;

	public ChartNameInvalidException() {
		super("name must not be blank");
	}
}
