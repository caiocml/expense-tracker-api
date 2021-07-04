package br.com.caiocesar.expense.tracker.api.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.caiocesar.expense.tracker.api.util.Crypto;

@Entity
@Table(name = "et_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PaymentType> paymentTypes;
	
	public User() {
		
	}
	
	public User(Integer userId, String firstName, String lastName, String email, String password) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		setPassword(password);
	}
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		setPassword(password);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Crypto.encrypt(password);
	}

	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public String getUniqueHash() {
		StringBuffer token = new StringBuffer("");
		token.append(getEmail());
		token.append(getPassword());
		return Crypto.encrypt(token.toString(), 10);
	}
	
//	public void addPaymentType(PaymentType paymentType) {
//		Objects.requireNonNull(paymentType, " paymentType must not be null");
//		paymentTypes();
//		paymentTypes.add(paymentType);
//		paymentType.setUser(this);
//	}
//
//	private void paymentTypes() {
//		if(getPaymentTypes() == null)
//			setPaymentTypes(new ArrayList<>());
//	}
//	
	
	

}
