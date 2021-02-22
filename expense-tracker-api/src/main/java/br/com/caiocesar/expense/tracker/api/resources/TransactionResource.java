package br.com.caiocesar.expense.tracker.api.resources;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.services.TransactionService;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> addTransaciton(HttpServletRequest request, 
			@PathVariable Integer categoryId, @RequestBody Map<String, Object> body){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		final Double amount = (Double) body.get("amount");
		final String note = (String) body.get("note");
		
		//final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String transactionDateTime = (String) body.get("transactionDate");
		
		LocalDateTime transactionDate = null;
		try {
			transactionDate = LocalDateTime.parse(transactionDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}catch (DateTimeParseException e) {
			map.put("message", "invalid date format, should be yyyy-MM-dd HH:mm:ss");
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = transactionService.addTransaction(user.getUserId(), categoryId, amount, note, transactionDate);
		
		map.put("created", "OK");
		map.put("transaction", transaction);
		
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> getTransaction(HttpServletRequest request, @PathVariable Integer transactionId) {
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		return new ResponseEntity<Transaction>(transactionService.fetchTransactionById(user.getUserId(), transactionId), HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Transaction>> allUserCategoryTransactions(HttpServletRequest request,
			@PathVariable Integer categoryId){
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		return new ResponseEntity<>(transactionService.fetchAllTransactions(user.getUserId(), categoryId), HttpStatus.OK);
	}
	
	@PutMapping("{transactionId}")
	public ResponseEntity<Map<String, Object>> alterTransaction(HttpServletRequest request,
																@PathVariable Integer categoryId, 
																@PathVariable Integer transactionId,
																@RequestBody Transaction transaction)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		transactionService.updateTransaction(user.getUserId(), transactionId, categoryId, transaction);
		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Map<String, Object>> deleteTransaction(HttpServletRequest request,
		@PathVariable Integer transactionId, @PathVariable Integer categoryId){
		Map<String, Object> map = new HashMap<>();
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
	
		transactionService.removeTransaction(transactionId, categoryId, user);
		
		map.put("deleted", true);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
}
