package com.bpark.guestbook.redis.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bpark.guestbook.object.Guest;

@Repository
public class GuestRepository {
	
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
		hashOperations.put( KEY, id, guestEntry );
	}

	public void delete(final String id) {
		hashOperations.delete( KEY, id );
	}

	public Map<Object, Object> findAllMessages(){
		return hashOperations.entries( KEY );
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