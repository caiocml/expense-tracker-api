package br.com.caiocesar.expense.tracker.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.caiocesar.expense.tracker.api.domain.PaymentType;
import br.com.caiocesar.expense.tracker.api.repository.PaymentTypeRepo;

@Service
public class PaymentTypeService {
	
	@Autowired
	private PaymentTypeRepo repository;
	
	public PaymentType save(PaymentType paymentType) {
		return repository.save(paymentType);
	}
	
	public List<PaymentType> getAllFromUser(Integer userId){
		var sample = new PaymentType();
		sample.setUserId(userId);
		return repository.findAll(Example.of(sample));
	}

	public Optional<PaymentType> findByIdAndUserId(Integer paymentTypeId, Integer userId) {
		return repository.findByIdAndUserId(paymentTypeId, userId);
	}

}
