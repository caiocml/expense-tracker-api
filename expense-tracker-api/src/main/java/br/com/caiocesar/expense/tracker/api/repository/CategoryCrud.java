package br.com.caiocesar.expense.tracker.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.caiocesar.expense.tracker.api.domain.Category;

public interface CategoryCrud extends CrudRepository<Category, Integer> {

}
