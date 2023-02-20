package br.com.caiocesar.expense.tracker.api.projections;

import java.time.LocalDateTime;

import br.com.caiocesar.expense.tracker.api.payment.InvoiceType;

public interface TransactionRelatory {
	
	Integer getTransactionId();
	Double getAmount();
	String getNote();
	String getPaymentDescription();
	InvoiceType getInvoiceType();
	String getCategoryDescription();
	LocalDateTime getDate();
	Integer getCategoryId();
	
	String getTitle();
	String getCategoryName();
	
	/**
	+ " t.amount as amount,"
			+ " t.note as note,"
			+ " p.description as paymentDescription,"
			+ " t.invoiceType as invoiceType,"
			+ " c.description as categoryDescription,"
			+ " t.transactionDate as Date"
			**/

}
