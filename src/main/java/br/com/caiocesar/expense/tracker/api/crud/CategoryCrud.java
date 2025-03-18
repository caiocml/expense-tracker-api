package br.com.caiocesar.expense.tracker.api.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.projections.DescriptionCategoryOnly;

//@Repository
public interface CategoryCrud extends CrudRepository<Category, Integer> {
	
	
	List<Category> findByTitle(String title);
	
	List<DescriptionCategoryOnly> findByDescriptionStartingWith(String description);
	
	void deleteByUserIdAndId(Integer userId, Integer categoryId);
	
	Category findByIdAndUserId(Integer categoryId, Integer userId);
	
	List<Category> findByUserId(Integer userId);
	

}
