package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoEansException extends RuntimeException {

	private static final long serialVersionUID = -5356020281436260106L;

	public NoEansException() {
		super("there has to be at least on ean");
	}
}