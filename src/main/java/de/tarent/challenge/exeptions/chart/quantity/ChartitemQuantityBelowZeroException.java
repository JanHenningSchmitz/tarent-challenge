package de.tarent.challenge.exeptions.chart.quantity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = ChartitemQuantityBelowZeroException.MESSAGE)
public class ChartitemQuantityBelowZeroException extends RuntimeException {

	public static final String MESSAGE = "quantity must be > 0";
	public static final HttpStatus STATUS = HttpStatus.CONFLICT;

	private static final long serialVersionUID = -6121980453131098708L;

	public ChartitemQuantityBelowZeroException(String sku, int quantity) {
		super("quantity " + quantity + " of " + sku + " must be > 0");
	}
}
