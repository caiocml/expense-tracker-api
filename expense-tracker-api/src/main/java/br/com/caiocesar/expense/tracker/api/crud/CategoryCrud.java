package br.com.caiocesar.expense.tracker.api.crud;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.projections.DescriptionCategoryOnly;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryCrud extends CrudRepository<Category, Integer> {
	
	
	List<Category> findByTitle(String title);
	
	List<DescriptionCategoryOnly> findByDescriptionStartingWith(String description);
	
	void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);
	
	Category findByCategoryIdAndUserId(Integer categoryId, Integer userId);
	
	List<Category> findByUserId(Integer userId);
	

}
