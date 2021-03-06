package com.microservice.user.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.model.UserRecord;
import com.microservice.user.registration.DAO.UserRegistrationDAOImplementation;
import com.microservice.user.registration.exception.UserRegistrationGenericException;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;


//@RibbonClient(name = "user-search-delete-service", configuration = UserSearchDeleteRibbonConfiguration.class)

@RefreshScope
@RestController
public class UserRegistrationController  {
	
	@Autowired UserRegistrationDAOImplementation userRegDAO;
	@Autowired	RestTemplate restTemplate;
	

    @Autowired
    private EurekaClient eurekaClient;
    
 // @Value("${spring.application.name}")
 // private String applicationName;
 
    @Value("${eureka.instance.instance-id}")
    private String instanceId;
  
	// REST API Calling Method: POST
	// http://localhost:8080/CreateUserUri?userid=1&&password=pass&&firstname=suvendu&&lastname=mandal
	
	   @RequestMapping(value = "/CreateUserUri",  method = RequestMethod.POST)
	   public ResponseEntity<Object> createUserUri(@RequestParam("userid") String UserId, @RequestParam("password") String Password, 
			   @RequestParam("firstname") String FirstName,   @RequestParam("lastname") String LastName ) {	
		  
		//   System.out.println("Application Name:" +  applicationName);
		   
			 //Retrieving the UserSearchDelete Microservice URL from Eureka Server
		   Application application = eurekaClient.getApplication( "USER-SEARCH-DELETE-SERVICE" );
		   InstanceInfo instanceInfo = application.getInstances().get(0);
		  // System.out.println("failed here");
		   String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "SearchUser/" + UserId;
		   System.out.println("Called Microservice URL: " + url);
		   boolean UserIdExists = restTemplate.getForObject(url , boolean.class );
		   
			// boolean UserIdExists = restTemplate.getForObject("http://localhost:8091/SearchUser/"+UserId, boolean.class );

			   if (UserIdExists) return new ResponseEntity<>("User Id already exists.", HttpStatus.CONFLICT); 
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(FirstName)) return new ResponseEntity<>("User First Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(LastName)) return new ResponseEntity<>("User Last Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.createUsers(UserId, FirstName, LastName, Password ) >= 1){
			      return new ResponseEntity<>("User has been created successfully", HttpStatus.CREATED);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
	   
		// REST API Calling Method: POST
		// http://localhost:8080/CreateUser
	   //In Msg Body:
	   //{
		//"userid":"1",
		//"password":"pass",
		//"firstname":"Suvendu",
		//"lastname":"Mandal"
	//  }
	   
	   @RequestMapping(value = "/CreateUser", headers="Content-Type=application/json", method = RequestMethod.POST)
	   public ResponseEntity<Object> createUser(@RequestBody UserRecord userRecBody ) {	
		   
		   String UserId = userRecBody.getUserid();
		   String FirstName = userRecBody.getFirstname();
		   String LastName = userRecBody.getLastname();
		   String Password = userRecBody.getPassword();
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(FirstName)) return new ResponseEntity<>("User First Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(LastName)) return new ResponseEntity<>("User Last Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		          
		//   System.out.println("Called Application Name:" +  applicationName);
		   
		 //Retrieving the UserSearchDelete Microservice URL from Eureka Server
		
//		  Application application = eurekaClient.getApplication( "user-search-delete-service" );
//		  InstanceInfo instanceInfo = application.getInstances().get(0);
//		 
//		   //System.out.println("failed here");
//		   String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "SearchUser/" + UserId;
//		   System.out.println("Called Microservice URL: " + url);
//		   boolean UserIdExists = restTemplate.getForObject(url , boolean.class );
		   
			// boolean UserIdExists = restTemplate.getForObject("http://localhost:8091/SearchUser/"+UserId, boolean.class );
		    boolean UserIdExists = restTemplate.getForObject("http://USER-SEARCH-DELETE-SERVICE/SearchUser/"+UserId, boolean.class );
		  
			  
		   if (UserIdExists) return new ResponseEntity<>("User Id already exists.", HttpStatus.CONFLICT); 
		   

		   if(userRegDAO.createUsers(UserId, FirstName, LastName, Password) >= 1){
			      return new ResponseEntity<>("User has been created successfully", HttpStatus.CREATED);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
		// REST API Calling Method: PUT
		// http://localhost:8081/DeleteUser/1
	   @RequestMapping(value = "/DeleteUser/{userid}" , method = RequestMethod.POST)
	   public ResponseEntity<Object> deleteUser(@PathVariable("userid") String UserId) {	   
		   
		   System.out.println("Within User Registration. UserId:" + UserId); 
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   int DeleteStatusCode = restTemplate.getForObject("http://USER-SEARCH-DELETE-SERVICE/CallDeleteUser/"+UserId, int.class );
		   
		   if(DeleteStatusCode == 200){
			      return new ResponseEntity<>("User has been deleted successfully", HttpStatus.OK);
	        }else{
	        	return new ResponseEntity<>("User cannot be deleted. Internal error.", HttpStatus.BAD_REQUEST);
	        }
	   }


		// REST API Calling Method: GET
		// http://localhost:8080/GetAllUsers
	   
	   @RequestMapping(value = "/GetAllUsers", method = RequestMethod.GET)
	   public ResponseEntity<Object> fetchAllUsers() {	
		   return new ResponseEntity<>(userRegDAO.getAllUsers(), HttpStatus.OK);		   
	   }

		// REST API Calling Method: PUT
		// http://localhost:8081/UpdateUserDetails/1
	   
	   //In Msg Body:
	   //{
		//"firstname":"Suvendu",
		//"lastname":"Mandal"
	//  }
	   
	   @RequestMapping(value = "/UpdateUserDetails/{userid}", headers="Content-Type=application/json", method = RequestMethod.PUT)
	   public ResponseEntity<Object> updateUserDetails(@PathVariable("userid") String userid, @RequestBody UserRecord userRecBody) { 
	
		   //String UserId = userRecBody.getUserid();
		   String UserId = userid ;
		   String FirstName = userRecBody.getFirstname();
		   String LastName = userRecBody.getLastname();
		   
		   if(isNullOrEmpty(UserId)) return new ResponseEntity<>("User Id cannot be blank.", HttpStatus.BAD_REQUEST); 
		   
		   if(userRegDAO.updateUser(UserId, FirstName, LastName) >= 1){
			      return new ResponseEntity<>("User Details updated successfully", HttpStatus.OK);
	        }else{
	        	throw new UserRegistrationGenericException();
	        }
	   }
	   
	   
		// REST API Calling Method: PUT
		// http://localhost:8080/UpdateUserDetailsUri?userid=1&&firstname=suvendu&&lastname=mandal
	   
	   @RequestMapping(value = "/UpdateUserDetailsUri",  method = RequestMethod.PUT)
	   public ResponseEntity<Object> updateUserDetailsUri(@RequestParam("userid") String UserId, 
			   @RequestParam("firstname") String FirstName,   @RequestParam("lastname") String LastName) { 

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
