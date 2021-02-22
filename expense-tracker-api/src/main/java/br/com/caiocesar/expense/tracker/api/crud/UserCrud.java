package br.com.caiocesar.expense.tracker.api.crud;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.User;

public interface UserCrud extends CrudRepository<User, Integer>{

}
