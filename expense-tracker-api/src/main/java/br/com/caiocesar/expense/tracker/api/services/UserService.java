package br.com.caiocesar.expense.tracker.api.services;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;

public interface UserService {
	
	
	User validateUser(String email, String password) throws AuthorizationException;
	
	User registerUser(String firstName, String lastName, String email, String password) throws AuthorizationException;

}
