package br.com.caiocesar.expense.tracker.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;


@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
	
	private static final String SQL_FIND_BY_TITLE = "SELECT ID FROM ET_CATEGORIES WHERE TITLE LIKE ?";
	
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
		// TODO Auto-generated method stub
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
	public void update(Integer userId, Integer categoryId, Category category) throws NotFoundException, BusinessException {
		User user = fethUser(userId);
		
		Category entity = new Category();
		
		entity = findById(categoryId);
		
		if(entity.getUserId().equals(user.getUserId()))
		{
			category.setUserId(entity.getCategoryId());
			categoryCrud.save(category);
		}else
		{
			throw new BusinessException("category : " + categoryId + " does not belong to user id: " + userId);
		}
	}
	
	private User fethUser(Integer userId) {
		return userRepository.findById(userId);
	}

	@Override
	public void removeById(Integer userId, Integer categoryID) {
		// TODO Auto-generated method stub
		
	}

}
