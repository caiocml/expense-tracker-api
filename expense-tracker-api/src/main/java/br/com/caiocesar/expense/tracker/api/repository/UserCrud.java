package br.com.caiocesar.expense.tracker.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.User;

public interface UserCrud extends CrudRepository<User, Integer>{

}
