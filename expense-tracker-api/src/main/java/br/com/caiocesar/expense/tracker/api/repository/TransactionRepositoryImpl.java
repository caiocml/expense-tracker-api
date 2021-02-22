package br.com.caiocesar.expense.tracker.api.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.crud.TransactionCrud;
import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{
	
	@Autowired
	TransactionCrud transactionCrud;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String SQL_ALL_USER_TRANSACTIONS_CATEGORY = "SELECT TRANSACTION_ID FROM ET_TRANSACTIONS WHERE "
			+ "USER_ID = ? AND CATEGORY_ID = ?";

	@Override
	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		
		List<Integer> ids = jdbcTemplate.queryForList(SQL_ALL_USER_TRANSACTIONS_CATEGORY, new Object[]{userId, categoryId}, Integer.class);
		
		if(ids != null)
			return (List<Transaction>) transactionCrud.findAllById(ids);

		throw new NotFoundException("not found any transaciton");
	}

	@Override
	public Transaction findById(Integer userId, Integer transactionId) {
		
		Optional<Transaction> transaction = transactionCrud.findById(transactionId);
		
		if(transaction.isPresent())
			if(transaction.get().getUserId().equals(userId))
				return transaction.get();
		
		throw new NotFoundException("not found a transaction: " + transactionId + " for user: " + userId);
	}

	@Override
	public Transaction create(Integer userId, Integer categoryId, Double amount, String note,
			LocalDateTime transactionDate) throws BusinessException {
		return transactionCrud.save(new Transaction(userId, categoryId, amount, note, transactionDate));
	}



	@Override
	public void delete(Transaction transaction) throws NotFoundException {
		transactionCrud.delete(transaction);
	}

	@Override
	public Transaction updateTransaction(Integer userId, Integer transactionId, Integer categoryId,
			Transaction newTransaction) throws NotFoundException {
		
		Transaction entity = findById(userId, transactionId);
		
		entity.setAmount(newTransaction.getAmount());
		entity.setNote(newTransaction.getNote());
		entity.setTransactionDate(newTransaction.getTransactionDate());
		
		return transactionCrud.save(entity);
	}

}
