package br.com.caiocesar.expense.tracker.api.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.caiocesar.expense.tracker.api.Constants;
import br.com.caiocesar.expense.tracker.api.domain.Category;
import br.com.caiocesar.expense.tracker.api.domain.Token;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.repository.CategoryService;
import br.com.caiocesar.expense.tracker.api.services.UserService;
import br.com.caiocesar.expense.tracker.api.util.Crypto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/users")
public class UserResources {
	
	@Autowired
	UserService userService;
		
	@PostMapping("/login")
	public ResponseEntity<UserAutenticated> login(@RequestBody Map<String,String> body){
		
		String email = body.get("email");
		String password = body.get("password");
		
		User user = userService.validateUser(email, password);
		Map<String,String> map = new HashMap<>();
		map.put("message","login sucessfull");
		String userToken = getUserToken(user);

		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setUserId(user.getUserId());
		
		return new ResponseEntity<UserAutenticated>(new UserAutenticated(userDto, userToken), HttpStatus.OK);
		
	}
	
	@PutMapping("/alterPassword")
	public ResponseEntity<Map<String, String>> alterUser(@RequestBody Map<String,String> body){
		
		Map<String, String> map = new HashMap<String, String>();
		
		String email = body.get("email");
		String password = body.get("password");
		String newPassword = body.get("newPassword");
		String newPasswordConfirmation = body.get("newPasswordConfirmation");
		
		User user = userService.alterUserPassword(email, password, newPassword, newPasswordConfirmation);
		
		map.put("message", "password Changed for user: " + user.getEmail());
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.ACCEPTED);
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, String> bodyRequest) {		
		User user = userService.registerUser(bodyRequest.get("firstName"), bodyRequest.get("lastName"), bodyRequest.get("email"), bodyRequest.get("password"));
		
		Map<String, String> map = new HashMap<>();
		map.put("sucess", "user created sucesful!");
		map.put("token", getUserToken(user));
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	private String getUserToken(User user) {
		return generateToken(user).getToken();
	}
	
	private Token generateToken(User user) {
		long generatedTime = System.currentTimeMillis();
		String token = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
				.setIssuedAt(new Date(generatedTime))
				.setExpiration(new Date(generatedTime + Constants.TOKEN_EXPIRE_TIME))
				.claim("userId", user.getUserId())
				.claim("email", user.getEmail())
				.claim("firtName", user.getFirstName())
				.claim("lastName", user.getLastName())
				.claim("serverToken", user.getUniqueHash())
				.compact();
	
		return new Token(token);
	}

	
	
	

}
