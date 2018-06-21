package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChartIsNullException extends RuntimeException {

	private static final long serialVersionUID = -5021249292317420552L;

	public ChartIsNullException() {
		super("chart should not be null");
	}
}
