package br.com.caiocesar.expense.tracker.api.resources;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.repository.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
	
	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	public String getAllCategories(HttpServletRequest request) {
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		return "Authenticated, welcome: " + user.getFirstName();
	}
	
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createCategory(HttpServletRequest request,
			@RequestBody Map<String, Object> body) {
		
		Map<String, Object> map = new HashMap<>();

		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		final String title = (String) body.get("title");
		final String description = (String) body.get("description");
		
		Category category = categoryService.addCategory(user.getUserId(), title, description);
		map.put("created", "OK");
		map.put("category", category);
		
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	
	
	
//	@PostMapping("/listCategories/{id}")
//	ResponseEntity<Map<String, Object>> testes(@RequestBody Map<String, String> body, @PathVariable Integer id) {
//		Map<String, Object> map = new HashMap<>();
//		
//		Category category = categoryService.
//		map.put("message", "sucess");
//		map.put("category", category);
//		
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}
	
}
