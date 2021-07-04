package br.com.caiocesar.expense.tracker.api.responses;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;

public class TransactionRelatoryResponse extends GenericPaginationResponse<TransactionRelatory> {

	private List<TransactionRelatory> transactions;
	
	public TransactionRelatoryResponse(Page<TransactionRelatory> page) {
		super(page, true);
		this.transactions = page.getContent();
	}

	public List<TransactionRelatory> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionRelatory> transactions) {
		this.transactions = transactions;
	}
	
}
