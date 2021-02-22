package br.com.caiocesar.expense.tracker.api.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.crud.CategoryCrud;
import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;


@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
	
	private static final String SQL_FIND_BY_TITLE = "SELECT ID FROM ET_CATEGORIES WHERE TITLE LIKE ?";
	private static final String SQL_FIND_ALL = "SELECT DISTINCT CATEGORY_ID FROM ET_CATEGORIES WHERE USER_ID = ?";
	private static final String SQL_DELETE_ALL_CATEGORY_TRANSACTIONS = "DELETE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ?";
	private static final String SQL_DELETE_CATEGORY= "DELETE FROM ET_CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";

	
	@Autowired
	CategoryCrud categoryCrud;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Category findById(Integer id) {
		Optional<Category> category = categoryCrud.findById(id);
		
		if(category.isPresent())
			return category.get();
		
		throw new NotFoundException("category not found with id: " + id);
	}

	@Override
	public List<Category> findAll(Integer userId) throws NotFoundException {
		
		try {
			
			List<Integer> ids = jdbcTemplate.queryForList(SQL_FIND_ALL, new Object[]{userId}, Integer.class);
			
			if(ids != null) {
				return new ArrayList<Category>((Collection<? extends Category>) categoryCrud.findAllById(ids));
			}
			
		}catch(Exception e){
		
		}
		
		
		return null;
	}
	
	@Override
	public Category create(Integer userId, String title, String description) throws BusinessException {
	
		User user = fethUser(userId);
		
		Category category = new Category();
		category.setUserId(user.getUserId());
		category.setTitle(title);
		category.setDescription(description);
				
		categoryCrud.save(category);
		
		return category;
	}
	
	@Override
	public Category update(Integer userId, Integer categoryId, Category category) throws NotFoundException, BusinessException {
		User user = fethUser(userId);
		
		Category entity = new Category();
		
		entity = findById(categoryId);
		
		if(entity.getUserId().equals(user.getUserId()))
		{
			
			entity.setDescription(category.getDescription());
			entity.setTitle(category.getTitle());
			
			if(entity.getDescription() == null || entity.getTitle() == null || entity.getDescription().isEmpty() || entity.getTitle().isEmpty()) 
				throw new BusinessException("must indicate a valid description and title");
			
		return categoryCrud.save(entity);
		}else
		{
			throw new BusinessException("category : " + categoryId + " does not belong to user id: " + userId);
		}
	}
	
	private User fethUser(Integer userId) {
		return userRepository.findById(userId);
	}

	@Override
	public void removeById(Integer userId, Integer categoryId) {
		removeAllCategoryTransactions(userId, categoryId);
		jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] {userId, categoryId});
	}

	private void removeAllCategoryTransactions(Integer userId, Integer categoryId) {
		jdbcTemplate.update(SQL_DELETE_ALL_CATEGORY_TRANSACTIONS, new Object[]{userId, categoryId});
	}
	
	

}
