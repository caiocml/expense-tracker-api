package br.com.caiocesar.expense.tracker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415167660919781537L;

	public AuthorizationException(String message) {
		super(message);
	}

}
