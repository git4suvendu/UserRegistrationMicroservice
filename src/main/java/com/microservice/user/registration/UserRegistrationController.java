package com.microservice.user.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.microservice.user.model.UserRecord;
import com.microservice.user.registration.DAO.*;
import com.microservice.user.registration.exception.UserRegistrationGenericException;


@RestController
public class UserRegistrationController  {
	
	@Autowired UserRegistrationDAOImplementation userRegDAO;
	
	   @RequestMapping(value = "/CreateUserUri",  method = RequestMethod.POST)
	   public ResponseEntity<Object> createUserUri(@RequestParam("userid") String UserId, @RequestParam("password") String Password, 
			   @RequestParam("firstname") String FirstName,   @RequestParam("lastname") String LastName ) {	
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(FirstName)) return new ResponseEntity<>("User First Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(LastName)) return new ResponseEntity<>("User Last Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.createUser(UserId, FirstName, LastName) >= 1){
			      return new ResponseEntity<>("User has been created successfully", HttpStatus.CREATED);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
	   @RequestMapping(value = "/CreateUser", headers="Content-Type=application/json", method = RequestMethod.POST)
	   public ResponseEntity<Object> createUser(@RequestBody UserRecord userRecBody ) {	
		   
		   String UserId = userRecBody.getUserid();
		   String FirstName = userRecBody.getFirstname();
		   String LastName = userRecBody.getLastname();
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(FirstName)) return new ResponseEntity<>("User First Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(LastName)) return new ResponseEntity<>("User Last Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.createUser(UserId, FirstName, LastName) >= 1){
			      return new ResponseEntity<>("User has been created successfully", HttpStatus.CREATED);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }


	   @RequestMapping(value = "/GetAllUsers", method = RequestMethod.GET)
	   public ResponseEntity<Object> fetchAllUsers() {	
		   return new ResponseEntity<>(userRegDAO.getAllUsers(), HttpStatus.OK);		   
	   }

	   
	   @RequestMapping(value = "/UpdateUserDetails/{userid}", headers="Content-Type=application/json", method = RequestMethod.PUT)
	   public ResponseEntity<Object> updateUserDetails(@PathVariable("userid") String userid, @RequestBody UserRecord userRecBody) { 
		   //if(!productRepo.containsKey(id))throw new ProductNotfoundException();
		   String UserId = userRecBody.getUserid();
		   String FirstName = userRecBody.getFirstname();
		   String LastName = userRecBody.getLastname();
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.updateUser(UserId, FirstName, LastName) >= 1){
			      return new ResponseEntity<>("User Details updated successfully", HttpStatus.OK);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
	   @RequestMapping(value = "/UpdateUserDetailsUri",  method = RequestMethod.PUT)
	   public ResponseEntity<Object> updateUserDetailsUri(@RequestParam("userid") String UserId, 
			   @RequestParam("firstname") String FirstName,   @RequestParam("lastname") String LastName) { 
		   //if(!productRepo.containsKey(id))throw new ProductNotfoundException();
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.updateUser(UserId, FirstName, LastName) >= 1){
			      return new ResponseEntity<>("User Details updated successfully", HttpStatus.OK);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
	   public static boolean isNullOrEmpty(String str) {
	        if(str != null && !str.isEmpty())
	            return false;
	        return true;
	    }
	   
}
