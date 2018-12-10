package com.bpark.guestbook.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bpark.guestbook.object.Guest;
import com.bpark.guestbook.redis.repository.GuestRepository;


@Controller
public class GuestbookController {
	
	@Autowired
	GuestRepository guestRepository;
	
	private static AtomicInteger ID_GENERATOR = new AtomicInteger( 1000 );

	@PostMapping( path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<Guest> postAdd( @RequestBody Guest guest ) {
		guest.setId( ID_GENERATOR.getAndIncrement() + "" );
		guest.setDateAdded( new Date() );
		guestRepository.add( guest.getId(), guest );
		System.out.println( "Added Guest: " + guest.toString() );
		return new ResponseEntity<Guest>( guest, HttpStatus.OK );
	}
	
	@GetMapping( path = "/find", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Map<Object, Object>> getFind() {
		return new ResponseEntity<Map<Object, Object>>( guestRepository.findAllMessages(), HttpStatus.OK );
	}
	
	@GetMapping( path = "/clear", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity getClear() {
		guestRepository.deleteAll();
		return new ResponseEntity( HttpStatus.OK );
	}	

}