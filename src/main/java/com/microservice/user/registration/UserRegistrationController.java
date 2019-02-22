package com.microservice.user.registration;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.model.UserModel;



@RestController
public class UserRegistrationController {
	
	   @RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	   public ResponseEntity<Object> createUser(@RequestBody UserModel user) {
	      productRepo.put(product.getId(), product);
	      return new ResponseEntity<>("Products are created successfully", HttpStatus.CREATED);
	   }

}
