package br.com.caiocesar.expense.tracker.api.responses;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.caiocesar.expense.tracker.api.dto.TransactionDTO;

public class TransactionDTOResponse extends GenericPaginationResponse<TransactionDTO>{

	private List<TransactionDTO> transactions;
	
	public TransactionDTOResponse(Page<TransactionDTO> page) {
		super(page, true);
		this.transactions = page.getContent();
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

}
