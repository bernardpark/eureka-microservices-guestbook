package com.bpark.guestbook.redis.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bpark.guestbook.object.Guest;

@Repository
public class GuestRepository {
	
	Logger logger = LoggerFactory.getLogger( GuestRepository.class );
	
	private String KEY = "guestbook";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public GuestRepository( RedisTemplate<String, Object> redisTemplate, String topic ) {
		this.redisTemplate = redisTemplate;
		this.KEY = topic;
	}

	@PostConstruct
	private void init(){
		hashOperations = redisTemplate.opsForHash();
	}

	public void add( final String id, final Guest guestEntry ) {
		logger.debug( "REDIS PUT: [" + KEY + "] " + guestEntry.toString() );
		hashOperations.put( KEY, id, guestEntry );
		logger.debug( "REDIS PUT: [" + KEY + "] SUCCESSFUL" );

	}

	public void delete(final String id) {
		logger.debug( "REDIS DELETE: [" + KEY + "] " + id );
		hashOperations.delete( KEY, id );
		logger.debug( "REDIS DELETE: [" + KEY + "] SUCCESSFUL" );
	}

	public Map<Object, Object> findAllMessages(){
		logger.debug( "REDIS ENTRIES: [" + KEY + "]" );
		Map<Object, Object> allMessages = hashOperations.entries( KEY );
		logger.debug( "REDIS ENTRIES: [" + KEY + "] SUCCESSFUL" );
		
		return allMessages;
	}

	public void deleteAll() {
		if ( findAllMessages() != null ) {
			for ( Object value : findAllMessages().values() ) {
				Guest guestEntry = ( Guest ) value;
				hashOperations.delete(KEY, guestEntry.getId() );
			}
		}
	}

}