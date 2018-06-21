package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CouldNotCheckOutChartException extends RuntimeException {

	private static final long serialVersionUID = 2809560136487667010L;

	public CouldNotCheckOutChartException(String name) {
		super("could not check out chart '" + name + "'.");
	}
}
