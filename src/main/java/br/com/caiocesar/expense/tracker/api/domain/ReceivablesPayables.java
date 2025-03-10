package br.com.caiocesar.expense.tracker.api.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "et_receivables_payables")
public class ReceivablesPayables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivablePayableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id",updatable = false)
    private Transaction transaction;

    private BigDecimal amount;

    private LocalDate dueDate;

    private Status status;

    private LocalDate paymentDate;

    private Integer installment;

    public Long getReceivablePayableId() {
        return receivablePayableId;
    }

    public void setReceivablePayableId(Long receivablePayableId) {
        this.receivablePayableId = receivablePayableId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getInstallment() {
        return installment;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
