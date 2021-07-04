package br.com.caiocesar.expense.tracker.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;

public interface TransactionRepository {
	
	List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

	Transaction findById(Integer userId, Integer transactionId);
	
	Transaction create(Integer userId, Integer categoryId, Double amount, String note, LocalDateTime transactionDate) throws BusinessException;
	
	Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId, Transaction newTransaction) throws NotFoundException;
	
	void delete(Transaction transactionId) throws NotFoundException;
	
	void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);
	
	Transaction save(Transaction transaction);
	
	Page<Transaction> findAll(Example<Transaction> sample, Pageable page);
	
	Page<TransactionRelatory> transactionRelatory(Integer size, Integer page, Integer userId);


}
