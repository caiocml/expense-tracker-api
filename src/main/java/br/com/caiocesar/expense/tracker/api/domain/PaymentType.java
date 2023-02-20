package br.com.caiocesar.expense.tracker.api.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "et_payment_type")
public class PaymentType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	private Integer lastCardNumber;
	private String cardBrandName;
	private String cardBankName;
	private Long accountNumber;
	private Integer bankNumber;
	private Integer agencyNumber;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",updatable = false, insertable = false)
	private User user;
		
	@OneToMany(mappedBy = "paymentType", fetch = FetchType.LAZY)
	private List<Transaction> transactions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLastCardNumber() {
		return lastCardNumber;
	}

	public void setLastCardNumber(Integer lastCardNumber) {
		this.lastCardNumber = lastCardNumber;
	}

	public String getCardBrandName() {
		return cardBrandName;
	}

	public void setCardBrandName(String cardBrandName) {
		this.cardBrandName = cardBrandName;
	}

	public String getCardBankName() {
		return cardBankName;
	}

	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(Integer bankNumber) {
		this.bankNumber = bankNumber;
	}

	public Integer getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(Integer agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	
	
	
	
	
}
