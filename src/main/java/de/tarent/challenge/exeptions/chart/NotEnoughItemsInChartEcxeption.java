package de.tarent.challenge.exeptions.chart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = NotEnoughItemsInChartEcxeption.MESSAGE)
public class NotEnoughItemsInChartEcxeption extends RuntimeException {

	public static final String MESSAGE = "Not enough Items in chart";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = 6353407232966328398L;

	public NotEnoughItemsInChartEcxeption(String name, int quantity) {
		super("Not enough Items in chart + " + name + " to delete " + quantity);
	}
}
