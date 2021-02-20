package br.com.caiocesar.expense.tracker.api.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

	
	@GetMapping("")
	public String getAllCategories(HttpServletRequest request) {
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		return "Authenticated, welcome: " + user.getFirstName();
	}
	
}
