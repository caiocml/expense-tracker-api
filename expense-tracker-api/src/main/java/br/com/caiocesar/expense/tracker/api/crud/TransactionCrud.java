package br.com.caiocesar.expense.tracker.api.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;

public interface TransactionCrud extends CrudRepository<Transaction, Integer>{

	void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);
	
	List<Transaction> findByUserIdAndCategoryId(Integer userId, Integer categoryId);

	
}
