package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRespository;

	@Override
	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		return transactionRespository.fetchAllTransactions(userId, categoryId);
	}

	@Override
	public Transaction fetchTransactionById(Integer userId, Integer transactionId) {
		return transactionRespository.findById(userId, transactionId);
	}

	@Override
	public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			LocalDateTime transactionDate) throws BusinessException {
		return transactionRespository.create(userId, categoryId, amount, note, transactionDate);
	}

	@Override
	public Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId, Transaction newTransaction)
			throws NotFoundException {
		return transactionRespository.updateTransaction(userId,transactionId, categoryId, newTransaction);
	}

	@Override
	public void removeTransaction(Integer transactionId,Integer categoryId, User user) throws NotFoundException, BusinessException {
		Transaction transaction = transactionRespository.findById(user.getUserId(), transactionId);
		
		if(transaction.getCategoryId().equals(categoryId))
		{
			transactionRespository.delete(transaction);
		}
		else
		{
			throw new BusinessException("Transaction is not valid for the category: " + categoryId);
		}
	}

}
