package com.bpark.guestbook.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bpark.guestbook.object.Guest;
import com.bpark.guestbook.redis.repository.GuestRepository;


@Controller
public class GuestbookController {
	
	@Autowired
	GuestRepository guestRepository;
	
	private static AtomicInteger ID_GENERATOR = new AtomicInteger( 1000 );

	@PostMapping( path = "/guestbook-add", consumes = "application/json", produces = "application/json" )
	public ResponseEntity postGuestbookAdd( @RequestBody Guest guest ) {
		guest.setId( ID_GENERATOR.getAndIncrement() + "" );
		guest.setDateAdded( new Date() );
		guestRepository.add( guest.getId(), guest );
		System.out.println( "Adding the following guest: " + guest.toString() );
		
		return new ResponseEntity( guest, HttpStatus.OK );
	}
	
	@GetMapping( "/guestbook-find" )
	public Map<Object, Object> getGuestbookFind() {
		return guestRepository.findAllMessages();
	}
	
	@GetMapping( "/guestbook-clear" )
	public ResponseEntity getGuestbookClear() {
		return new ResponseEntity( HttpStatus.OK );
	}	

}