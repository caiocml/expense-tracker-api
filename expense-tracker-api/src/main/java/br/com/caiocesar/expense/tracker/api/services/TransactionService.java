package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;

public interface TransactionService {
	
	List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);
	
	Transaction fetchTransactionById(Integer userId, Integer transactionId);
	
	Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, LocalDateTime transactionDate) throws BusinessException;
	
	Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId, Transaction newTransaction) throws NotFoundException;
	
	void removeTransaction(Integer transactionId, User user) throws NotFoundException, BusinessException;
	
	Transaction save(Transaction transaction);
	
	Page<Transaction> findPageable(Integer size, Integer page, Integer userid);
	
	Page<TransactionRelatory> relatory(Integer size, Integer page, Integer userId);

}
