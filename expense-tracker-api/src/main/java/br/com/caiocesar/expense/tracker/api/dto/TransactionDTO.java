package br.com.caiocesar.expense.tracker.api.dto;

import java.time.LocalDateTime;

import br.com.caiocesar.expense.tracker.api.payment.InvoiceType;

public class TransactionDTO implements GenericDto{

	private Double amount;
	private String note;
	private LocalDateTime transactionDate;
	private PaymentTypeDTO paymentType;
	private InvoiceType invoiceType;
	private Integer paymentTypeId;
	
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
	public InvoiceType getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}
	public PaymentTypeDTO getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentTypeDTO paymentType) {
		this.paymentType = paymentType;
	}
	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	
	
	
}
