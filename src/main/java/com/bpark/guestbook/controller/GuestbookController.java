package com.bpark.guestbook.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger( GuestbookController.class );

	@Autowired
	GuestRepository guestRepository;

	private static AtomicInteger ID_GENERATOR = new AtomicInteger( 1000 );

	@SuppressWarnings("finally")
	@PostMapping( path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<Guest> postAdd( @RequestBody Guest guest ) {
		guest.setId( ID_GENERATOR.getAndIncrement() + "" );
		guest.setDateAdded( new Date() );

		try {
			guestRepository.add( guest.getId(), guest );
			logger.info( "Added Guest: " + guest.toString() );
			return new ResponseEntity<Guest>( guest, HttpStatus.OK );
		}
		
		catch ( Exception e ) {
			logger.error( "Failed to add Guest: " + guest.toString() );
			logger.error( "Exception is: " + e.toString() );
		}
		
		finally {
			return new ResponseEntity<Guest>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}

	@SuppressWarnings("finally")
	@GetMapping( path = "/find", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Map<Object, Object>> getFind() {
		try {
			Map<Object, Object> allMessages = guestRepository.findAllMessages();
			logger.info( "Found " + allMessages.size() + " Guests" );
			for ( Object o : allMessages.values() ) {
				Guest guest = ( Guest ) o;
				logger.trace( "  Found " + guest.toString() );
				return new ResponseEntity<Map<Object, Object>>( allMessages, HttpStatus.OK );
			}
		}
		
		catch ( Exception e ) {
			logger.error( "Failed to find Guests." );
			logger.error( "Exception is: " + e.toString() );
		}
		
		finally {
			return new ResponseEntity<Map<Object, Object>>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}

	@SuppressWarnings("finally")
	@GetMapping( path = "/clear", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity getClear() {
		try {
			guestRepository.deleteAll();
			logger.info( "Deleted all Guests" );
			return new ResponseEntity<Map<Object, Object>>( HttpStatus.OK );
		}
		
		catch ( Exception e ) {
			logger.error( "Failed to delete Guests." );
			logger.error( "Exception is: " + e.toString() );
		}
		
		finally {
			return new ResponseEntity<Map<Object, Object>>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}	

}