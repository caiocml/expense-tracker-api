package br.com.caiocesar.expense.tracker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExpcetion extends RuntimeException {
	
	public NotFoundExpcetion(String message) {
		super(message);
	}

}
