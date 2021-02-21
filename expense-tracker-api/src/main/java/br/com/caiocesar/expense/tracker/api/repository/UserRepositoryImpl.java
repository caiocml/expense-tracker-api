package br.com.caiocesar.expense.tracker.api.repository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.exceptions.BusinessExeption;
import br.com.caiocesar.expense.tracker.api.exceptions.NotFoundExpcetion;
import br.com.statix.util.util.Criptografia;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) "
			+ "VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
	
	private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ? ";

	private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID FROM ET_USERS WHERE EMAIL = ?";;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserCrud userCrud;

//	@Deprecated
//	@Override
//	public Integer createUser(String firstName, String lastName, String email, String password) throws AuthorizationException {
//		try {
//			String encriptedPassWord = Criptografia.criptografar(password);
//			
//			
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			jdbcTemplate.update(connection ->{
//				
//				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
//			//	int i = 0;
//				ps.setString(1, firstName);
//				ps.setString(2, lastName);
//				ps.setString(3, email);
//				ps.setString(4, encriptedPassWord);
//				return ps;
//				
//			}, keyHolder);
//			
//			return (Integer) keyHolder.getKeys().get("USER_ID");
//		}catch (Exception e) {
//			throw new AuthorizationException("error on creating a user: " + e.getMessage());
//		}
//	}
	

	@Override
	public User findByEmailAndPassword(String email, String password) throws AuthorizationException {
		
		try {
			
			Integer userId = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[] {email}, Integer.class);
			if (userId == null)
				throw new NotFoundExpcetion("User not found");
			
			else {
				User user = userCrud.findById(userId).get();
				if(user.getPassword().equals(Criptografia.criptografar(password))) return user;
			
			else 
				throw new AuthorizationException("invalid email or password");
			}
			
		}catch(EmptyResultDataAccessException e) {
			throw new AuthorizationException("invalid email or password");
		}
		
		
	}

	@Override
	public Integer getCountByEmail(String email) {
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[] {email},Integer.class);
	}

	@Override
	public Optional<User> findById(Integer id) {
		//return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {id}, userRowMapper);
		return userCrud.findById(id);
	}
	
	private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
		return new User(rs.getInt("USER_ID"), 
				rs.getString("FIRST_NAME"), 
				rs.getString("LAST_NAME"),
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"));
	});

	@Override
	public Integer createUser(String firstName, String lastName, String email, String password)
			throws AuthorizationException {
		
		User user = new User(firstName, lastName, email, password);
		userCrud.save(user);
		
		return user.getUserId();
	}

	@Override
	public User alterUser(User user) throws AuthorizationException, BusinessExeption {
		
		if(user.getUserId() == null) throw new BusinessExeption("must provide a ID for alter USER");
		
		userCrud.save(user);
		
		return user;
	}


}
