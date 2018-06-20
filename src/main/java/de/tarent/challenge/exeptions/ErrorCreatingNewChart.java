package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ErrorCreatingNewChart extends RuntimeException {

	private static final long serialVersionUID = -2563978179340108032L;

	public ErrorCreatingNewChart() {
		super("could not create new chart");
	}
}
