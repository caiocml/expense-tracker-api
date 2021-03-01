package br.com.caiocesar.expense.tracker.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.projections.DescriptionCategoryOnly;
import br.com.caiocesar.expense.tracker.api.repository.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
	
	@Autowired
	CategoryService categoryService;

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
	
	
	
	
	@GetMapping("/{categoryId}")
	ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId) {
		Category category = categoryService.fetchCategoryById(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	

	@GetMapping("")
	ResponseEntity<List<Category>> ListAllUserCategory(HttpServletRequest request) {
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		List<Category> category = categoryService.listAllCategorires(user.getUserId());
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@PutMapping("/alter/{categoryId}")
	ResponseEntity<Category> alterCategory(HttpServletRequest request, @PathVariable Integer categoryId,
			@RequestBody Map<String, String> requestBody) {
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		final String newDescription = requestBody.get("description");
		final String newTitle = requestBody.get("title");
		
		Category category = new Category(newTitle, newDescription);
		category = categoryService.updateCategory(user.getUserId(), categoryId, category);
		
		
		return new ResponseEntity<>(category, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Map<String, Object>> deleteCategory(HttpServletRequest request,@PathVariable Integer categoryId){
		Map<String, Object> map = new HashMap<>();
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		categoryService.removeCategoryWithAllTransactions(user.getUserId(), categoryId);
		
		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/name/{title}")
	public ResponseEntity<List<DescriptionCategoryOnly>> findCategoriesByTitle(HttpServletRequest request, @PathVariable String title){
		
		//User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		return new ResponseEntity<List<DescriptionCategoryOnly>>(categoryService.findByDescriptionLike(title), HttpStatus.OK);
	}
	
}
