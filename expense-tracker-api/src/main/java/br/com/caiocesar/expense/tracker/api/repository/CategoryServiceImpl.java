package br.com.caiocesar.expense.tracker.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> listAllCategorires(Integer userId) {
		return categoryRepository.findAll(userId);
	}

	@Override
	public Category fetchCategoryById(Integer categoryId) throws NotFoundException {
		return categoryRepository.findById(categoryId);
	}

	@Override
	public Category addCategory(Integer userId, String title, String description) throws BusinessException {
		return categoryRepository.create(userId, title, description);
	}

	@Override
	public Category updateCategory(Integer userId, Integer categoryId, Category category) throws BusinessException {
		return categoryRepository.update(userId, categoryId, category);
	}

	@Override
	public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws NotFoundException {
		// TODO Auto-generated method stub

	}

}
