package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import br.com.caiocesar.expense.tracker.api.requests.AlterTransactionRequest;

@Service
@Transactional
public class  TransactionService {
	
	@Autowired
	TransactionRepository transactionRespository;

	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		return transactionRespository.fetchAllTransactions(userId, categoryId);
	}

	
	public Transaction fetchTransactionById(Integer userId, Integer transactionId) {
		return transactionRespository.findById(userId, transactionId);
	}

	
	public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			LocalDateTime transactionDate) throws BusinessException {
		return transactionRespository.create(userId, categoryId, amount, note, transactionDate);
	}

	
	public void removeTransaction(Integer transactionId, User user) throws NotFoundException, BusinessException {
		
		Runnable action  = () -> {
			throw new BusinessException("Transaction is not found for user " + user.getEmail());
		};
		
		Consumer<Transaction> deleteTransaction = transaction -> transactionRespository.delete(transaction);
		
		Optional.ofNullable(transactionRespository.findById(user.getUserId(), transactionId))
			.ifPresentOrElse(deleteTransaction, action);
	}


	public Transaction save(Transaction transaction) {
		return transactionRespository.save(transaction);
	}

	
	public Page<Transaction> findPageable(Integer size, Integer page, Integer userId) {
		var sample = new Transaction();
		sample.setUserId(userId);
		
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, new String[]{"transactionId"}));
		
		return transactionRespository.findAll(Example.of(sample), pageable);
	}

	
	public Page<TransactionRelatory> relatory(Integer size, Integer page, Integer userId) {
		return transactionRespository.transactionRelatory(size, page, userId);
	}

	
	public Page<TransactionRelatory> periodRelatory(LocalDate startDate, LocalDate endDate, Integer category,
			User user, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, new String[]{"categoryName"}));
		return transactionRespository.periodRelatory(pageable, startDate, endDate, user);
	}


	public Transaction updateTransaction(@Valid AlterTransactionRequest transaction, Integer userId) {
		Optional<Transaction> optional = transactionRespository.findByTransactionIdAndUserId(transaction.getTransactionId(), userId);
		if(optional.isEmpty())
			throw new NotFoundException("Not found transaction id" + transaction.getTransactionId() + ", for current user");
		else {
			Transaction model = optional.get();
			changeTransactionFields(transaction, model);
			transactionRespository.save(model);
			return model;
		}
		
	}


	private void changeTransactionFields(@Valid AlterTransactionRequest transaction, Transaction model) {
		BeanUtils.copyProperties(transaction, model);
	}

}
