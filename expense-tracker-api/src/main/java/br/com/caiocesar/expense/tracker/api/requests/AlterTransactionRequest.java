package br.com.caiocesar.expense.tracker.api.requests;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import br.com.caiocesar.expense.tracker.api.payment.InvoiceType;

public class AlterTransactionRequest {

	@NotNull
	private Integer transactionId;
	
	@NotNull
	private Integer categoryId;
	
	@NotNull
	private Double amount;
	
	@NotNull
	private String note;
	
	@NotNull
	private LocalDateTime transactionDate;
	
	@NotNull
	private Integer paymentTypeId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private InvoiceType invoiceType;

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
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
	
	
	
}
