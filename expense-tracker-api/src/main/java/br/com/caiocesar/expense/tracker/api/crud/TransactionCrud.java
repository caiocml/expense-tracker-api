package br.com.caiocesar.expense.tracker.api.crud;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;

public interface TransactionCrud extends CrudRepository<Transaction, Integer>{

}
