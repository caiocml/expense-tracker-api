package br.com.caiocesar.expense.tracker.api.domain;

public class Token {
	
	private String token;
	
	public Token(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
