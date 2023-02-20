package br.com.caiocesar.expense.tracker.api.repository;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

public interface UserRepository {
	
	Integer createUser(String firstName, String lastName, String email, String password) throws AuthorizationException;
	
	User findByEmailAndPassword(String email, String password) throws AuthorizationException;
	
	Integer getCountByEmail(String email);
	
	User findById(Integer id)throws NotFoundException;
	
	User alterUser(User user)throws AuthorizationException, BusinessException;

}
