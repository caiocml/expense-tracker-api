package br.com.caiocesar.expense.tracker.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

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
import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.dto.CategoryDTO;
import br.com.caiocesar.expense.tracker.api.dto.PaymentTypeDTO;
import br.com.caiocesar.expense.tracker.api.dto.TransactionDTO;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.projections.DescriptionCategoryOnly;
import br.com.caiocesar.expense.tracker.api.repository.CategoryService;

@RestController
@RequestMapping("categories")
public class CategoryResource extends GenericResource {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Map<String, Object> body) {

		Map<String, Object> map = new HashMap<>();

		User user = getSessionUser();

		final String title = (String) body.get("title");
		final String description = (String) body.get("description");

		Category category = categoryService.addCategory(user.getUserId(), title, description);
		map.put("created", "OK");
		map.put("category", category);

		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@GetMapping("/{categoryId}")
	ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId, @PathParam("fillTransactions") boolean fillTransactions,
			@PathParam("fillPayment") boolean fillPayment) {
		Category category = categoryService.fetchCategoryById(categoryId);
		return new ResponseEntity<>(categoryModelToDto(category, fillTransactions, fillPayment), HttpStatus.OK);
	}

	@GetMapping("")
	ResponseEntity<List<CategoryDTO>> ListAllUserCategory(@PathParam("fillTransactions") boolean fillTransactions,
			@PathParam("fillPayment") boolean fillPayment) {

		User user = getSessionUser();

		List<Category> category = categoryService.listAllCategorires(user.getUserId());
		List<CategoryDTO> list = new ArrayList<>();
		
		fillList(category, list, fillTransactions, fillPayment);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private void fillList(List<Category> category, List<CategoryDTO> list, final boolean fillTransactions, final boolean fillPayment) {
		Optional.ofNullable(category)
			.ifPresent(then -> {
				then.forEach(model -> {
					var categoryDto = categoryModelToDto(model, fillTransactions, fillPayment);
					list.add(categoryDto);
				});
		});

		
	}

	private CategoryDTO categoryModelToDto(Category model, boolean fillTransactions, boolean fillPayment) {
		var categoryDto = (CategoryDTO) modelToDto(model, new CategoryDTO());
		if(fillTransactions) {
			var listTransactions = model.getTransactions();
			var listTransactionsDto = new ArrayList<TransactionDTO>();
			if (listTransactions != null)
				listTransactions.forEach(t -> {
					listTransactionsDto.add(transacionModelToDto(t, fillPayment));
				});
			categoryDto.setTransactions(listTransactionsDto);
		}
		return categoryDto;
	}

	private TransactionDTO transacionModelToDto(Transaction transaction, boolean fillPayment) {
		var transactionDto = (TransactionDTO) modelToDto(transaction, new TransactionDTO());
		if(fillPayment)
		{
			var paymentType = transaction.getPaymentType();
			if(paymentType !=  null) {
				PaymentTypeDTO paymentDTO = (PaymentTypeDTO) modelToDto(paymentType, new PaymentTypeDTO());
				transactionDto.setPaymentType( paymentDTO);
			}
		}
		return transactionDto;
	}

	@PutMapping("/alter/{categoryId}")
	ResponseEntity<CategoryDTO> alterCategory(HttpServletRequest request, @PathVariable Integer categoryId,
			@RequestBody Map<String, String> requestBody) {

		User user = getSessionUser();

		final String newDescription = requestBody.get("description");
		final String newTitle = requestBody.get("title");
		if(newDescription == null || newTitle == null)
			throw new BusinessException("invalid name or description");
		
		Category category = new Category(newTitle, newDescription);
		category = categoryService.updateCategory(user.getUserId(), categoryId, category);

		return new ResponseEntity<>(categoryModelToDto(category, false, false), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Map<String, Object>> deleteCategory(HttpServletRequest request,
			@PathVariable Integer categoryId) {
		Map<String, Object> map = new HashMap<>();

		User user = getSessionUser();

		categoryService.removeCategoryWithAllTransactions(user.getUserId(), categoryId);

		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@GetMapping("/name/{title}")
	public ResponseEntity<List<DescriptionCategoryOnly>> findCategoriesByTitle(HttpServletRequest request,
			@PathVariable String title) {

		// User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);

		return new ResponseEntity<List<DescriptionCategoryOnly>>(categoryService.findByDescriptionLike(title),
				HttpStatus.OK);
	}

}
