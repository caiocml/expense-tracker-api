package br.com.caiocesar.expense.tracker.api;

import java.util.Base64;

public class Constants {
	
	public static final String API_SECRET_KEY = Base64.getEncoder().encodeToString("dlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdladlaldjalkdjalkdjalkdjalkjdla".getBytes());
	
	public static final long TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000;

}
