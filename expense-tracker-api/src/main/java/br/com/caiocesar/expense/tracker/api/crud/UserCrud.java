package br.com.caiocesar.expense.tracker.api.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.caiocesar.expense.tracker.api.domain.User;

public interface UserCrud extends CrudRepository<User, Integer>{
	
	User findByEmailAndPassword(String email, String password);
	
	@Query(value = "SELECT count(*) FROM ET_USERS WHERE EMAIL = :email", nativeQuery = true)
	Integer countEmailAndPassword(@Param(value = "email") String email);
	
	
}
