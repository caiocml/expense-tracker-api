package br.com.caiocesar.expense.tracker.api.resources;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import br.com.caiocesar.expense.tracker.api.domain.Transaction;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.dto.PaymentTypeDTO;
import br.com.caiocesar.expense.tracker.api.dto.TransactionDTO;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.projections.TransactionRelatory;
import br.com.caiocesar.expense.tracker.api.responses.TransactionDTOResponse;
import br.com.caiocesar.expense.tracker.api.responses.TransactionRelatoryResponse;
import br.com.caiocesar.expense.tracker.api.services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionResource extends GenericResource{
	
	private static final Integer DEFAULT_SIZE = 100;

	@Autowired
	TransactionService transactionService;

	@PostMapping("{categoryId}/register")
	public ResponseEntity<TransactionDTO> saveTransaction(@PathVariable Integer categoryId, @RequestBody TransactionDTO body){
		
		User user = getSessionUser();
		
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(body, transaction);
		transaction.setCategoryId(categoryId);
		transaction.setUserId(user.getUserId());
		
		if(transaction.getTransactionDate() == null)
			transaction.setTransactionDate(LocalDateTime.now());
		
		transaction = transactionService.save(transaction);
		
		TransactionDTO transactionDTO = new TransactionDTO();
		BeanUtils.copyProperties(transaction, transactionDTO);
		
		return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
	}
	
//	@GetMapping("/{transactionId}")
//	public ResponseEntity<Transaction> getTransaction(@PathVariable Integer transactionId) {
//		User user = getSessionUser();
//		
//		return new ResponseEntity<Transaction>(transactionService.fetchTransactionById(user.getUserId(), transactionId), HttpStatus.OK);
//	}
	
	@GetMapping("{categoryId}")
	public ResponseEntity<List<TransactionDTO>> allUserCategoryTransactions(HttpServletRequest request,
			@PathVariable Integer categoryId){
		User user = getSessionUser();
		
		List<TransactionDTO>dtoList = new ArrayList<>();
		List<Transaction> fetchAllTransactions = transactionService.fetchAllTransactions(user.getUserId(), categoryId);
		
		copyListProperties(fetchAllTransactions, dtoList);
		
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}
	
	@GetMapping("/relatory")
	public ResponseEntity<TransactionRelatoryResponse> getTransactionRelatoryOfUser(@PathParam("size") Integer size, @PathParam("page") Integer page){
		User user = getSessionUser();

		Page<TransactionRelatory> relatory = transactionService.relatory(size, page, user.getUserId());
		var response = new TransactionRelatoryResponse(relatory);
		return new ResponseEntity<TransactionRelatoryResponse>(response, HttpStatus.OK);
	}
	
	private void copyListProperties(List<Transaction> list, List<TransactionDTO> dtoList) {
		Optional.ofNullable(list).ifPresent(l -> l.forEach(element ->{
			dtoList.add(domainToModel(element));
		}));
	}

	@PutMapping("{transactionId}")
	public ResponseEntity<Map<String, Object>> alterTransaction(@PathVariable Integer categoryId, 
																@PathVariable Integer transactionId,
																@RequestBody Transaction transaction)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = getSessionUser();
		
		transactionService.updateTransaction(user.getUserId(), transactionId, categoryId, transaction);
		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Map<String, Object>> deleteTransaction(@PathVariable Integer transactionId){
		Map<String, Object> map = new HashMap<>();
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
	
		transactionService.removeTransaction(transactionId, user);
		
		map.put("deleted", true);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUserTransactions(@PathParam("size") Integer size ,@PathParam("page") Integer page){
		User user = getSessionUser();

		if(page == null)
			page = 1;
		if(size == null)
			size = DEFAULT_SIZE;
		Page<Transaction> search = transactionService.findPageable(size, page, user.getUserId());
		
		List<TransactionDTO> dtoList = new ArrayList<>();
		Optional.ofNullable(search).ifPresent(e->{
			
			e.forEach(transaction -> dtoList.add(domainToModel(transaction)));
			
		});
		
		var response = new TransactionDTOResponse(new PageImpl<TransactionDTO>(dtoList, search.getPageable(), search.getTotalElements()));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private TransactionDTO domainToModel(Transaction transaction) {
		var dto = new TransactionDTO();
		BeanUtils.copyProperties(transaction, dto);
		var paymentDto = new PaymentTypeDTO();
		BeanUtils.copyProperties(transaction.getPaymentType(),paymentDto);
		
		dto.setPaymentType(paymentDto);
		
		return dto;
		
		
	}
}
