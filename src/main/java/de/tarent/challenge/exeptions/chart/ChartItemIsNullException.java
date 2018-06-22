package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChartItemIsNullException extends RuntimeException {

	private static final long serialVersionUID = -7591576042067702056L;

	public ChartItemIsNullException() {
		super("item should not be null");
	}
}
