package br.com.caiocesar.expense.tracker.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.domain.PaymentType;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.dto.PaymentTypeDTO;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.services.PaymentTypeService;

@RestController
@RequestMapping("/paymentType")
public class PaymentTypeResource {
	
	@Autowired 
	HttpServletRequest request;
	
	@Autowired
	PaymentTypeService paymentTypeService;
	
	
	@PostMapping("")
	public ResponseEntity<?> createPaymentType(@RequestBody PaymentTypeDTO body){
		
		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);
		
		var paymentType = new PaymentType();
		BeanUtils.copyProperties(body, paymentType);
		
		paymentType.setUserId(user.getUserId());
		
		paymentType = paymentTypeService.save(paymentType);
		
		var dto = new PaymentTypeDTO();
		BeanUtils.copyProperties(paymentType, dto);
		
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public ResponseEntity<List<PaymentTypeDTO>> getUserPaymentType(){

		User user = (User) request.getAttribute(AuthFilter.USER_CONTEXT);

		List<PaymentType> search = paymentTypeService.getAllFromUser(user.getUserId());
		var dtoList = new ArrayList<PaymentTypeDTO>();
		Optional.ofNullable(search).ifPresent(lista -> lista.forEach(paymentType ->{
			var dto = new PaymentTypeDTO();
			BeanUtils.copyProperties(paymentType, dto);
			dtoList.add(dto);
		}));
		
		return new ResponseEntity<List<PaymentTypeDTO>>(dtoList, HttpStatus.OK);
	}
	
	

}
