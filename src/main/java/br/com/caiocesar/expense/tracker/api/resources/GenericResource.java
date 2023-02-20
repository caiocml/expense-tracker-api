package br.com.caiocesar.expense.tracker.api.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.dto.GenericDto;
import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;

@RestController
public class GenericResource {

	@Autowired
	protected HttpServletRequest request;
	
	protected User getSessionUser() {
		return (User) request.getAttribute(AuthFilter.USER_CONTEXT);
	}
	
	protected GenericDto modelToDto(Object model, GenericDto dto) {
		BeanUtils.copyProperties(model, dto);
		return  dto;
	}
}


