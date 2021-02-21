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
		
		return userRepository.findById(userId).get();
	}


	@Override
	public User alterUserPassword(String email, String password, String newPassword, String newPasswordConfirmation)
			throws AuthorizationException, BusinessExeption {
		
		if(email == null || password == null)
			throw new AuthorizationException("invalid email or password!");
		
		if(newPassword == null || newPasswordConfirmation == null)
			throw new BusinessExeption("invalid new password! ");
				
		if (newPassword.isEmpty() || newPassword.isEmpty())
			throw new BusinessExeption("invalid new password!");
	
		if (newPassword.equals(newPasswordConfirmation)) {

			User user = validateUser(email, password);
			user.setPassword(newPassword);
		
			userRepository.alterUser(user);
			
			return user;

		}else {
			throw new BusinessExeption("new password and confirmation must be the same!");
		}
	}


	


}
