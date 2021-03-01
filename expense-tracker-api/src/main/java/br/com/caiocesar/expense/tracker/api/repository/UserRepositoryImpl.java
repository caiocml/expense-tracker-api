package br.com.caiocesar.expense.tracker.api.repository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.crud.UserCrud;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundException;
import br.com.caiocesar.expense.tracker.api.util.Crypto;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) "
			+ "VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
	
	//private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ? ";

	private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID FROM ET_USERS WHERE EMAIL = ?";;
	
	@Autowired
	UserCrud userCrud;
	
	@Override
	public User findByEmailAndPassword(String email, String password) throws AuthorizationException {
		
		try {
		
			User user = userCrud.findByEmailAndPassword(email, Crypto.encrypt(password));
			
			
			if(user == null) 
				throw new AuthorizationException("invalid email or password");
			else 
				return user;
			
			
		}catch(EmptyResultDataAccessException e) {
			throw new AuthorizationException("invalid email or password");
		}
		
		
	}

	@Override
	public Integer getCountByEmail(String email) {
		return userCrud.countEmailAndPassword(email);
	}

	@Override
	public User findById(Integer id) {		
		Optional<User> user = userCrud.findById(id);
		
		if(user.isPresent())
			return user.get();
		
		throw new NotFoundException("user with id: " + id + " not found!");
	}
	

	@Override
	public Integer createUser(String firstName, String lastName, String email, String password)
			throws AuthorizationException {
		
		User user = new User(firstName, lastName, email, password);
		userCrud.save(user);
		
		return user.getUserId();
	}

	@Override
	public User alterUser(User user) throws AuthorizationException, BusinessException {
		
		if(user.getUserId() == null) throw new BusinessException("must provide a ID for alter USER");
		
		userCrud.save(user);
		
		return user;
	}
}
