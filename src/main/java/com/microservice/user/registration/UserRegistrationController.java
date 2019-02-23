package com.microservice.user.registration;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.model.UserRecord;


@RestController
public class UserRegistrationController {
	
	   @RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	   public ResponseEntity<Object> createUser(@RequestBody UserRecord user) {	
		   // Need to do something to add record in DB
		   
	      return new ResponseEntity<>("User has been created successfully", HttpStatus.CREATED);
	   }

	   @RequestMapping(value = "/GetAllUsers", method = RequestMethod.GET)
	   public ResponseEntity<Object> fetchAllUsers() {	
		   // Need to do something to fetch record from DB
		   
	      return null;
	   }

	   
}
