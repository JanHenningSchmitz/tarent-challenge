package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ItemNotInChartException.MESSAGE)
public class ItemNotInChartException extends RuntimeException {

	public static final String MESSAGE = "Item not in chart and cant be deletet";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;
	private static final long serialVersionUID = 1410405241957440146L;

	public ItemNotInChartException(String name, String sku, int quantity) {
		super("Item not in chart and cant be deletet (" + name + "," + sku + "," + quantity + ")");
	}
}
