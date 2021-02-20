package br.com.caiocesar.expense.tracker.api.services;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessExeption;
import br.com.caiocesar.expense.tracker.api.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User validateUser(String email, String password) throws AuthorizationException {
		if(email != null) email = email.toLowerCase();
		
		User user = userRepository.findByEmailAndPassword(email, password);
		return user;
	}

	@Override
	public User registerUser(String firstName, String lastName, String email, String password)
			throws AuthorizationException {

		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if(email != null) email = email.toLowerCase();
		if(!pattern.matcher(email).matches())
			throw new BusinessExeption("invalid email format");
		Integer count = userRepository.getCountByEmail(email);
		if(count > 0)
			throw new BusinessExeption("email already in use");
		
		Integer userId = userRepository.createUser(firstName, lastName, email, password);
		
		return userRepository.findById(userId);
	}

}
