package br.com.caiocesar.expense.tracker.api.repository;

import java.util.List;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.projections.DescriptionCategoryOnly;

public interface CategoryRepository {


	List<Category> findAll(Integer userId) throws NotFoundException;
	
	 Category findById(Integer id) throws NotFoundException;
	 
	 Category findByIdAndUserId(Integer categoryId, Integer userId);
	 
	 Category create(Integer userId, String title, String description) throws BusinessException;
	 
	 Category update(Integer userId, Integer categoryId, Category category) throws NotFoundException;
	 
	 void removeById(Integer userId, Integer categoryID);
	 
	 List<Category> findByTitle(String title);
	 
	 List<DescriptionCategoryOnly> findByDescriptionLike(String description);
}
