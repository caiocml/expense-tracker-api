package br.com.caiocesar.expense.tracker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BusinessExeption extends RuntimeException {
	
	public BusinessExeption(String message) {
		super(message);
	}

}
