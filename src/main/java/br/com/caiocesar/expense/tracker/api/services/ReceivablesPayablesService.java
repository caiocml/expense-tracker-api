package br.com.caiocesar.expense.tracker.api.services;

import br.com.caiocesar.expense.tracker.api.domain.*;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReceivablesPayablesService {

    private final PaymentTypeService paymentTypeService;

    public ReceivablesPayablesService(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    public List<ReceivablesPayables> createReceivablesPayables(Transaction transaction) {

        List<ReceivablesPayables> receivablesPayablesList = new ArrayList<>();
        if(transaction.getTransactionType() == TransactionType.SINGLE){
            var receivablePayable = createSingle(transaction);
            receivablesPayablesList.add(receivablePayable);
        }else if(transaction.getTransactionType() == TransactionType.INSTALLMENTS){

            List<ReceivablesPayables> installments = createInstallments(transaction);
            receivablesPayablesList.addAll(installments);

        }else{
            throw new BusinessException("Transaction type not found");
        }

        return receivablesPayablesList;
    }

    public List<ReceivablesPayables> createInstallments(Transaction transaction){

        List<ReceivablesPayables> receivablesPayablesList = new ArrayList<>();

        BigDecimal amount = transaction.getAmount()
                .divide(BigDecimal.valueOf(transaction.getInstallmentsNumber()), 2, RoundingMode.HALF_UP);

        final LocalDate today = LocalDate.now();
        for (int i =1 ; i <= transaction.getInstallmentsNumber(); i ++){

            var receivable = new ReceivablesPayables();
            receivable.setInstallment(i);
            receivable.setStatus(Status.OPEN);
            receivable.setAmount(amount);

            PaymentType paymentType = getPaymentType(transaction);

            Integer expirationDay = paymentType.getExpirationDay();
            if(expirationDay == null){
                expirationDay = 1;
            }

            YearMonth yearMonth = YearMonth.from(transaction.getTransactionDate());

            int monthsToAdd = i;

            if(paymentType.getDaysToCloseInvoice() != null){
                LocalDate lastDayOfInvoice = YearMonth.from(transaction.getTransactionDate())
                        .plusMonths(1)
                        .atDay(expirationDay)
                        .minusDays(paymentType.getDaysToCloseInvoice());

                LocalDate transactionLocalDate = LocalDate.from(transaction.getTransactionDate());
                if(transactionLocalDate.isEqual(lastDayOfInvoice) || transactionLocalDate.isAfter(lastDayOfInvoice)){
                    monthsToAdd = i + 1;
                }
            }

            yearMonth = yearMonth.plusMonths(monthsToAdd);

            LocalDate dueDate = yearMonth.atDay(expirationDay);

            receivable.setDueDate(dueDate);


            receivablesPayablesList.add(receivable);
        }

        return receivablesPayablesList;
    }


    public ReceivablesPayables createSingle(Transaction transaction){
        var receivable = new ReceivablesPayables();

        receivable.setInstallment(1);
        receivable.setAmount(transaction.getAmount());
        receivable.setStatus(Status.OPEN);

        PaymentType paymentType = getPaymentType(transaction);

        Integer expirationDay = paymentType.getExpirationDay();

        if(expirationDay == null){
            expirationDay = 1;
        }

        YearMonth yearMonth = YearMonth.from(transaction.getTransactionDate());

        yearMonth = yearMonth.plusMonths(1);

        LocalDate dueDate = yearMonth.atDay(expirationDay);

        receivable.setDueDate(dueDate);

        return receivable;
    }

    private PaymentType getPaymentType(Transaction transaction) {
        PaymentType paymentType = transaction.getPaymentType();

        if(paymentType == null){
            paymentType = paymentTypeService
                    .findByIdAndUserId(transaction.getPaymentTypeId(), transaction.getUserId())
                    .orElseThrow(() -> new BusinessException("Payment type not found"));
        }
        return paymentType;
    }

}
