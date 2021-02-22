package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDateTime;
import java.util.List;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

public interface TransactionService {
	
	List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);
	
	Transaction fetchTransactionById(Integer userId, Integer transactionId);
	
	Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, LocalDateTime transactionDate) throws BusinessException;
	
	Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId, Transaction newTransaction) throws NotFoundException;
	
	void removeTransaction(Integer transactionId, Integer categoryid, User user) throws NotFoundException, BusinessException;

}
