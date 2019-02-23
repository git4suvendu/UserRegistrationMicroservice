package com.microservice.user.registration.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.microservice.user.model.UserRecord;

@Repository
public class UserRegistrationDAOImplementation implements UserRegistrationDAO {
	
	@Autowired JdbcTemplate jdbcTemplate;

	@Override
	public String createUser(UserRecord user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRecord> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
