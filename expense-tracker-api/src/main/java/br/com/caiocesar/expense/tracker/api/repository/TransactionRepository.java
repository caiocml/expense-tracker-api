package br.com.caiocesar.expense.tracker.api.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.crud.TransactionCrud;
import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;

@Repository
public class TransactionRepository {
	
	@Autowired
	TransactionCrud transactionCrud;
	
	//@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	public void setCategoryService(@Lazy CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	private static final String SQL_ALL_USER_TRANSACTIONS_CATEGORY = "SELECT TRANSACTION_ID FROM ET_TRANSACTIONS WHERE "
			+ "USER_ID = ? AND CATEGORY_ID = ?";

	
	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		return transactionCrud.findByUserIdAndCategoryId(userId, categoryId);
	}

	
	public Transaction findById(Integer userId, Integer transactionId) {
		
		Optional<Transaction> transaction = transactionCrud.findById(transactionId);
		
		if(transaction.isPresent())
			if(transaction.get().getUserId().equals(userId))
				return transaction.get();
		
		throw new NotFoundException("not found a transaction: " + transactionId + " for user: " + userId);
	}

	
	public Transaction create(Integer userId, Integer categoryId, Double amount, String note,
			LocalDateTime transactionDate) throws BusinessException {
		
		Category category = categoryRepository.findByIdAndUserId(categoryId, userId);
		
		if(category == null)
			throw new NotFoundException("category not found with id: " + categoryId + " for user: " + userId);
		
		return transactionCrud.save(new Transaction(userId, categoryId, amount, note, transactionDate));
	}



	
	public void delete(Transaction transaction) throws NotFoundException {
		transactionCrud.delete(transaction);
	}

	
	public Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId,
			Transaction newTransaction) throws NotFoundException {
		
		Transaction entity = findById(userId, transactionId);
		
		entity.setAmount(newTransaction.getAmount());
		entity.setNote(newTransaction.getNote());
		entity.setTransactionDate(newTransaction.getTransactionDate());
		
		return transactionCrud.save(entity);
	}

	
	public void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId) {
		transactionCrud.deleteByUserIdAndCategoryId(userId, categoryId);
	}

	
	public Transaction save(Transaction transaction) {
		return transactionCrud.save(transaction);
	}

	
	public Page<Transaction> findAll(Example<Transaction> sample, Pageable page) {
		return transactionCrud.findAll(sample, page);
	}
	
	
	public Page<TransactionRelatory> transactionRelatory(Integer size, Integer page, Integer userId){
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.ASC, new String[]{"transactionId"}));
		return transactionCrud.relatory(userId, pageable);
	}

	
	public Page<TransactionRelatory> periodRelatory(Pageable pageable, LocalDate startDate, LocalDate endDate, User user) {
		return transactionCrud.periodRelatory(pageable, startDate, endDate, user.getUserId());
	}


	public Optional<Transaction> findById(Integer transactionId) {
		return transactionCrud.findById(transactionId);
	}


	public Optional<Transaction> findByTransactionIdAndUserId(Integer transactionId, Integer userId) {
		return transactionCrud.findByTransactionIdAndUserId(transactionId, userId);
	}


}
