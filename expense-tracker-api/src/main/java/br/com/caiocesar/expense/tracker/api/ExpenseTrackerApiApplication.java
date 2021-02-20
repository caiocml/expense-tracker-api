package br.com.caiocesar.expense.tracker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.repository.UserRepository;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<AuthFilter>();
		AuthFilter auth = new AuthFilter(userRepository);
		registrationBean.setFilter(auth);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
		
	}

}
