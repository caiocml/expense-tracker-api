package br.com.caiocesar.expense.tracker.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;

import br.com.caiocesar.expense.tracker.api.domain.*;
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

import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;
import br.com.caiocesar.expense.tracker.api.repository.TransactionRepository;
import br.com.caiocesar.expense.tracker.api.requests.AlterTransactionRequest;

@Service
@Transactional
public class  TransactionService {
	
	@Autowired
	private TransactionRepository transactionRespository;

	@Autowired
	private ReceivablesPayablesService receivablesPayablesService;

	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		return transactionRespository.fetchAllTransactions(userId, categoryId);
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

		if(transaction.getCreditDebit() == null){
			//padrao vai ser sempre um gasto e nao um recebimento
			transaction.setCreditDebit(CreditDebit.DEBIT);
		}

		if(transaction.getTransactionType() == null){
			transaction.setTransactionType(TransactionType.SINGLE);
			transaction.setInstallmentsNumber(1);
		}

		if(transaction.getTransactionType() == TransactionType.INSTALLMENTS ||
			transaction.getTransactionType() == TransactionType.RECURRING) {
			if (transaction.getInstallmentsNumber() == null || transaction.getInstallmentsNumber() <= 0) {
				throw new BusinessException("Installments number must be greater than 0");
			}
		}

		transaction.setCreatedAt(LocalDateTime.now());

		List<ReceivablesPayables> receivablesPayables = receivablesPayablesService.createReceivablesPayables(transaction);

		transaction.setReceivablesPayables(receivablesPayables);

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
