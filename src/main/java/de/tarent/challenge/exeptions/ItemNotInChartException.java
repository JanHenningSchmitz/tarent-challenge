package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemNotInChartException extends RuntimeException {

	private static final long serialVersionUID = 1410405241957440146L;

	public ItemNotInChartException(String name, String sku, int quantity) {
		super("Item not in chart and cant be deletet (" + name + "," + sku + "," + quantity + ")");
	}
}
