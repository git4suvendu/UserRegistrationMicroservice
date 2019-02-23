package com.microservice.user.registration.DAO;

import java.util.List;
import com.microservice.user.model.UserRecord;

public interface UserRegistrationDAO {
	public abstract String createUser (UserRecord user);	
	public abstract List<UserRecord> getAllUsers();
}
