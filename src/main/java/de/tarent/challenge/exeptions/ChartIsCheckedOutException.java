package de.tarent.challenge.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.tarent.challenge.store.chart.Chart;

@ResponseStatus(HttpStatus.CONFLICT)
public class ChartIsCheckedOutException extends RuntimeException {

	private static final long serialVersionUID = -1407032470488010345L;

	public ChartIsCheckedOutException(Chart chart) {
		super("chart is allready closed throug checkout " + chart.getName());
	}
}
