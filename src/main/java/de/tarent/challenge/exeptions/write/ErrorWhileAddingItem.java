package de.tarent.challenge.exeptions.write;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ErrorWhileAddingItem extends RuntimeException {

	private static final long serialVersionUID = 2571672855455548807L;

	public ErrorWhileAddingItem(String name, String sku, int quantity) {
		super("could not add "  + sku + "("+quantity+")"+ " to chart '" + name + "'.");
	}
}
