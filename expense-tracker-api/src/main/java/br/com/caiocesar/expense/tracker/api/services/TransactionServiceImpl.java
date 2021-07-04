package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;
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
	public void removeTransaction(Integer transactionId, User user) throws NotFoundException, BusinessException {
		
		Runnable action  = () -> {
			throw new BusinessException("Transaction is not found for user " + user.getEmail());
		};
		
		Consumer<Transaction> deleteTransaction = transaction -> transactionRespository.delete(transaction);
		
		Optional.ofNullable(transactionRespository.findById(user.getUserId(), transactionId))
			.ifPresentOrElse(deleteTransaction, action);
	}

	@Override
	public Transaction save(Transaction transaction) {
		return transactionRespository.save(transaction);
	}

	@Override
	public Page<Transaction> findPageable(Integer size, Integer page, Integer userId) {
		var sample = new Transaction();
		sample.setUserId(userId);
		
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, new String[]{"transactionId"}));
		
		return transactionRespository.findAll(Example.of(sample), pageable);
	}

	@Override
	public Page<TransactionRelatory> relatory(Integer size, Integer page, Integer userId) {
		return transactionRespository.transactionRelatory(size, page, userId);
	}
	

}
