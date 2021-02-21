package br.com.caiocesar.expense.tracker.api.repository;

import java.util.List;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

public interface CategoryService {
	
	List<Category> listAllCategorires(Integer userId);
	
	Category fetchCategoryById(Integer categoryId) throws NotFoundException;
	
	Category addCategory(Integer userId, String title, String description) throws BusinessException;

	void updateCategory(Integer userId, Integer categoryId, Category category) throws BusinessException;
	
	void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws NotFoundException;
}
