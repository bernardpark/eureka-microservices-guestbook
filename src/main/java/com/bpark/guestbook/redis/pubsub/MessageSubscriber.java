package com.bpark.guestbook.redis.pubsub;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber implements MessageListener {
	
	Logger logger = LoggerFactory.getLogger( MessageSubscriber.class );

    public static List<String> messageList = new ArrayList<String>();
    
    public void onMessage( final Message message, final byte[] pattern ) {
        messageList.add( message.toString() );
        logger.trace( "Recieved " + new String( message.getBody() ) + "." );
    }
    
}