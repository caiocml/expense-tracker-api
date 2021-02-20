package br.com.caiocesar.expense.tracker.api.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caiocesar.expense.tracker.api.Constants;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.domains.Token;
import br.com.caiocesar.expense.tracker.api.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/users")
public class UserResources {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody User body){
		User user = userService.validateUser(body.getEmail(), body.getPassword());
		Map<String,String> map = new HashMap<>();
		map.put("message","login sucessfull");
		map.put("token", getUserToken(user));
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody User bodyRequest) {		
		User user = userService.registerUser(bodyRequest.getFirstName(), bodyRequest.getLastName(), bodyRequest.getEmail(), bodyRequest.getPassword());
		
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
				.compact();
	
		return new Token(token);
	}
}
