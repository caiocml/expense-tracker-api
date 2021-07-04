package br.com.caiocesar.expense.tracker.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caiocesar.expense.tracker.api.payment.InvoiceType;

@Entity
@Table(name = "et_transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	private Integer userId;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id",updatable = false, insertable = false)
	private Category category;
	
	private Double amount;
	
	private String note;
	
	private LocalDateTime transactionDate;
	
	@Column(name = "payment_type_id")
	private Integer paymentTypeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_type_id", insertable = false, updatable = false)
	private PaymentType paymentType;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Enumerated(EnumType.STRING)
	private InvoiceType invoiceType;

	public Transaction() {
		
	}

	public Transaction(Integer userId, Integer categoryId, Double amount, String note, LocalDateTime transactionDate) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.amount = amount;
		this.note = note;
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}


	
	

}
