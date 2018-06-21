package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ErrorWhileDeletingChart extends RuntimeException {

	private static final long serialVersionUID = -6103352385377910112L;

	public ErrorWhileDeletingChart(String name) {
		super("can't delete chart " + name);
	}
}