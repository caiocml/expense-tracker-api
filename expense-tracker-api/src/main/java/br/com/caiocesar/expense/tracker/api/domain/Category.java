package br.com.caiocesar.expense.tracker.api.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "et_categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name ="user_id")
	private Integer userId;
	
	private String title;
	
	private String description;
	
	@Formula("(select count(*) from et_transactions t where t.category_id = category_id)")
	private Integer totalTransactions;

	@Formula("(select coalesce(sum(t.amount), 0.0) from et_categories right outer join et_transactions t on et_categories.category_id = t.category_id)")
	private Double totalExpense;

	@Formula("(select coalesce(sum(t.amount), 0.0) from et_categories et left join et_transactions t on et.category_id = t.category_id "
			+ "where et.category_id = category_id)")
	private Double totalCategoryExpenses;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Category(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public Category() {
		
	}

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

//	public Double getTotalExpense() {
//		return totalExpense;
//	}
//
//	public void setTotalExpense(Double totalExpense) {
//		this.totalExpense = totalExpense;
//	}
//
//	public Double getTotalCategoryExpenses() {
//		return totalCategoryExpenses;
//	}
//
//	public void setTotalCategoryExpenses(Double totalCategoryExpenses) {
//		this.totalCategoryExpenses = totalCategoryExpenses;
//	}
//
//	public Integer getTotalTransactions() {
//		return totalTransactions;
//	}
//
//	public void setTotalTransactions(Integer totalTransactions) {
//		this.totalTransactions = totalTransactions;
//	}

//	public User getUserModel() {
//		return userModel;
//	}
//
//	public void setUserModel(User userModel) {
//		this.userModel = userModel;
//	}


}
