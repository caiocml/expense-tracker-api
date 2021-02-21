package br.com.caiocesar.expense.tracker.api.repository;

import java.util.Optional;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessExeption;

public interface UserRepository {
	
	Integer createUser(String firstName, String lastName, String email, String password) throws AuthorizationException;
	
	User findByEmailAndPassword(String email, String password) throws AuthorizationException;
	
	Integer getCountByEmail(String email);
	
	Optional<User> findById(Integer id);
	
	User alterUser(User user)throws AuthorizationException, BusinessExeption;

}
