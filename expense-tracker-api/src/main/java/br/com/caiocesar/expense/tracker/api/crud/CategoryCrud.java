package br.com.caiocesar.expense.tracker.api.crud;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.Category;

public interface CategoryCrud extends CrudRepository<Category, Integer> {

}
