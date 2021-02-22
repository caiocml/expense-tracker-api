package br.com.caiocesar.expense.tracker.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

public interface TransactionRepository {
	
	List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

	Transaction findById(Integer userId, Integer transactionId);
	
	Transaction create(Integer userId, Integer categoryId, Double amount, String note, LocalDateTime transactionDate) throws BusinessException;
	
	Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId, Transaction newTransaction) throws NotFoundException;
	
	void delete(Transaction transactionId) throws NotFoundException;

}
