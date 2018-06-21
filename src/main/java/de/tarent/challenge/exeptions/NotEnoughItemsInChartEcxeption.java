package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotEnoughItemsInChartEcxeption extends RuntimeException {

	private static final long serialVersionUID = 6353407232966328398L;

	public NotEnoughItemsInChartEcxeption(String name, int quantity) {
		super("Not enough Items in chart + " + name + " to delete " + quantity);
	}
}
