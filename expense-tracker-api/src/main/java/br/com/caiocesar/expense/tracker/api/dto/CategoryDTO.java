package br.com.caiocesar.expense.tracker.api.dto;

import java.util.List;

public class CategoryDTO implements GenericDto {

	private Integer categoryId;
	private Integer userId;
	private String title;
	private String description;	
	private Integer totalTransactions;		
	private Double totalExpense;
	private Double totalCategoryExpenses;
	
	private List<TransactionDTO> transactions;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(Integer totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public Double getTotalCategoryExpenses() {
		return totalCategoryExpenses;
	}

	public void setTotalCategoryExpenses(Double totalCategoryExpenses) {
		this.totalCategoryExpenses = totalCategoryExpenses;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
	
	

}
