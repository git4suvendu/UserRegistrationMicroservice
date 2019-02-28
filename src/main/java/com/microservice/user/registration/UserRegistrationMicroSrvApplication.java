package com.microservice.user.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

 

//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class UserRegistrationMicroSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationMicroSrvApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
 
}
