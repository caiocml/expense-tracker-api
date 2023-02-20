package br.com.caiocesar.expense.tracker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6118207816853800272L;

	public NotFoundException(String message) {
		super(message);
	}

}
