package br.com.caiocesar.expense.tracker.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

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
		
//	@JsonIgnoreProperties({"hibernateLazyInitializer"})
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id" , insertable = false, updatable = false)
//	private User userModel;
	
	@Formula("(select coalesce(sum(t.amount), 0.0) from et_categories right outer join et_transactions t on et_categories.category_id = t.category_id)")
	private Double totalExpense;

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

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

//	public User getUserModel() {
//		return userModel;
//	}
//
//	public void setUserModel(User userModel) {
//		this.userModel = userModel;
//	}


}
