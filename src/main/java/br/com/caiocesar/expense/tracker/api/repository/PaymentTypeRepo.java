package br.com.caiocesar.expense.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.domain.PaymentType;

@Repository
public interface PaymentTypeRepo extends JpaRepository<PaymentType, Integer>{

	
}
