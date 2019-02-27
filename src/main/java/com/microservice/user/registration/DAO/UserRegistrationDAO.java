package com.microservice.user.registration.DAO;

import java.util.List;
import com.microservice.user.model.UserRecord;

public interface UserRegistrationDAO {
	public abstract int createUser (String UserId, String UserFirstName, String UserLastName, String UserPassword );	
	public abstract List<UserRecord> getAllUsers();
	public abstract int updateUser ( String UserId, String UserFirstName, String UserLastName);	
}
