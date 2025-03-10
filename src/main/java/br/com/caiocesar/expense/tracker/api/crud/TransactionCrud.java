package br.com.caiocesar.expense.tracker.api.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;

public interface TransactionCrud extends JpaRepository<Transaction, Integer>{

	void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);
	
	@Query("SELECT t FROM Transaction t "
			+ " WHERE userId = :userId AND categoryId = :categoryId")
	List<Transaction> findByUserIdAndCategoryId(@Param(value = "userId") Integer userId, @Param(value = "categoryId") Integer categoryId);

	@Query("SELECT"
			+ " c.title as categoryName,"
			+ " c.id as categoryId,"
			+ " t.transactionId as transactionId,"
			+ " t.amount as amount,"
			+ " t.note as note,"
			+ " p.description as paymentDescription,"
			+ " c.description as categoryDescription,"
			+ " t.transactionDate as date"
			+ " from Transaction t"
			+ " LEFT JOIN t.paymentType p" //on p.id=t.payment_type_id"
			+ " LEFT JOIN t.category c " //on t.category_id = c.category_id"
			+ " where t.userId = :userId")
	Page<TransactionRelatory> relatory(@Param(value = "userId" ) Integer userId, Pageable pageable);

	
	Optional<Transaction> findByTransactionIdAndUserId(Integer transactionId, Integer userId);

/*	
	@Query(value = "select"
			+ " sum(amount) as amount,"
			+ " c.title as categoryName"
			+ " from et_transactions t"
			+ " inner join et_categories c on c.category_id = t.category_id"
			+ " where t.user_id = :userId"
			//+ " and t.category_id = :category"
			+ " and transaction_date >= :startDate"
			+ " and transaction_date <= :endDate"
			+ " group by c.title",
			nativeQuery = true)
	List<TransactionRelatory> periodRelatory(
			Pageable pageable,
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, 
			@Param("userId")Integer userId);
	
	*/
	
	@Query(value = "select"
			+ " SUM(t.amount) as amount,"
			+ " c.title as categoryName"
			+ " from Transaction t"
			+ " INNER JOIN Category c ON c.categoryId = t.categoryId"
			+ " where t.userId = :userId"
			+ " and CAST(t.transactionDate as LocalDate) >= :startDate"
			+ " and CAST(t.transactionDate as LocalDate) <= :endDate"
			+ " GROUP BY c.title")
	Page<TransactionRelatory> periodRelatory(
			Pageable pageable,
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, 
			@Param("userId")Integer userId);
	
}	
