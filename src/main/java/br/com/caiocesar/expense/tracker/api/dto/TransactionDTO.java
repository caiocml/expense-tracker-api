package br.com.caiocesar.expense.tracker.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.caiocesar.expense.tracker.api.domain.CreditDebit;
import br.com.caiocesar.expense.tracker.api.domain.TransactionType;

public class TransactionDTO implements GenericDto{

	private Integer transactionId;
	private BigDecimal amount;
	private String note;
	private LocalDateTime transactionDate;
	private PaymentTypeDTO paymentType;
	private CreditDebit creditDebit;
	private Integer paymentTypeId;
	private TransactionType transactionType;
	private Integer installmentsNumber;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
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
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}


    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

	public Integer getInstallmentsNumber() {
		return installmentsNumber;
	}

	public void setInstallmentsNumber(Integer installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}

	public CreditDebit getCreditDebit() {
		return creditDebit;
	}

	public void setCreditDebit(CreditDebit creditDebit) {
		this.creditDebit = creditDebit;
	}
}
