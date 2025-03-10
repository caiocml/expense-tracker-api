package br.com.caiocesar.expense.tracker.api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import br.com.caiocesar.expense.tracker.api.payment.InvoiceType;

@Entity
@Table(name = "et_transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;

	@Column(nullable = false)
	private Integer userId;
	
	@Column(name = "category_id", nullable = false)
	private Integer categoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id",updatable = false, insertable = false)
	private Category category;

	@Column(nullable = false)
	private BigDecimal amount;
	
	private String note;
	
	private LocalDateTime transactionDate;
	
	@Column(name = "payment_type_id", nullable = false)
	private Integer paymentTypeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_type_id", insertable = false, updatable = false)
	private PaymentType paymentType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transaction", cascade = CascadeType.ALL)
	private List<ReceivablesPayables> receivablesPayables;

	private Integer installmentsNumber;

	private TransactionType transactionType;

	@Column(nullable = false)
	private CreditDebit creditDebit;
	
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


	public Transaction() {
		
	}

	public Transaction(Integer userId, Integer categoryId, BigDecimal amount, String note, LocalDateTime transactionDate) {
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

	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    public void setInstallmentsNumber(Integer installment) {
        this.installmentsNumber = installment;
    }

    public CreditDebit getCreditDebit() {
        return creditDebit;
    }

    public void setCreditDebit(CreditDebit creditDebit) {
        this.creditDebit = creditDebit;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public List<ReceivablesPayables> getReceivablesPayables() {
        return receivablesPayables;
    }

    public void setReceivablesPayables(List<ReceivablesPayables> receivablesPayables) {

		receivablesPayables.forEach(receivablePayable -> receivablePayable.setTransaction(this));

        this.receivablesPayables = receivablesPayables;
    }

	public void addReceivablePayable(ReceivablesPayables receivablePayable) {
		if(this.receivablesPayables == null){
			this.receivablesPayables = new ArrayList<>();
		}
		receivablePayable.setTransaction(this);
		this.receivablesPayables.add(receivablePayable);
	}
}
