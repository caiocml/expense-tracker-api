package br.com.caiocesar.expense.tracker.api.domain;

/**
 * Indica o sinal de uma transacao, se receita vai ser credito se despesa vai ser debito
 */
public enum CreditDebit {

    DEBIT,// GASTO, DESPESA
    CREDIT,// RECEBIMENTO, RECEITA
}
