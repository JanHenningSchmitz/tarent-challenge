package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2058279314171262383L;

	public ChartNotFoundException(String name) {
		super("could not find chart '" + name + "'.");
	}
}
