package com.microservice.user.registration.DAO;


import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.microservice.user.model.UserRecord;


@Repository
public class UserRegistrationDAOImplementation implements UserRegistrationDAO {
	
	private final String INSERT_SQL = "INSERT INTO USER_REGISTERED(LOGIN_ID, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?)";
	private final String SELECT_ALL_SQL = "SELECT * FROM  USER_REGISTERED" ;
	
	
	@Autowired JdbcTemplate jdbcTemplate;

	@Override
   public int createUser(String UserId, String UserFirstName, String UserLastName)  {
		return jdbcTemplate.update(INSERT_SQL,UserId,UserFirstName, UserLastName);

	}

	@Override
	public List<UserRecord> getAllUsers()   {
		RowMapper<UserRecord> rowMapper = new UserRowMapper();
		   return this.jdbcTemplate.query(SELECT_ALL_SQL, rowMapper);
	}

}
