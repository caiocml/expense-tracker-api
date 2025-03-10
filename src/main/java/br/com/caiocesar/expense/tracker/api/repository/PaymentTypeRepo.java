package br.com.caiocesar.expense.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.domain.PaymentType;

import java.util.Optional;

@Repository
public interface PaymentTypeRepo extends JpaRepository<PaymentType, Integer>{

    Optional<PaymentType> findByIdAndUserId(Integer paymentTypeId, Integer userId);
	
}
