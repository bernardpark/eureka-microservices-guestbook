package com.bpark.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GuestbookApplication {
	
	public static void main( String[] args ) {
		SpringApplication.run( GuestbookApplication.class, args );
	}
	
}
