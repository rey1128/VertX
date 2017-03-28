package com.rey.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class MessageVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();
		System.out.println("MessageVerticle: start");
		EventBus eBus=vertx.eventBus();
		
		// register to the "mv_Address" for listening messages
		eBus.consumer("mv_Address",message->{
			// when receive message
			// print the content of the message
			System.out.println("MV received a message: "+message.body());
			// send message to dummy verticle
//			eBus.send("dummy_Address", "nice to see you!");
		});
		
	}
	
}
