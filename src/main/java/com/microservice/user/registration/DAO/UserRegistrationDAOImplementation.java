package com.microservice.user.registration.DAO;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.microservice.user.model.UserRecord;


@Repository
public class UserRegistrationDAOImplementation implements UserRegistrationDAO {
	
	private final String INSERT_SQL = "INSERT INTO USER_REGISTERED(LOGIN_ID, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?)";
	
	@Autowired JdbcTemplate jdbcTemplate;

	@Override
   public int createUser(String UserId, String UserFirstName, String UserLastName) {
		return jdbcTemplate.update(INSERT_SQL,UserId,UserFirstName, UserLastName);

	}
	
	
	

	@Override
	public List<UserRecord> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
