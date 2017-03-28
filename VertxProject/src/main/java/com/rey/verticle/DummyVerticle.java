package com.rey.verticle;

import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class DummyVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();
		System.out.println("DummyVerticle: start");

		// get event bus instance
		EventBus eBus = vertx.eventBus();

		// register to the "dummy_Address" for listening messages
		eBus.consumer("dummy_Address", messsge -> {
			// when receive message
			// print the content of the message
			System.out.println("dummy received a message: " + messsge.body());
			// send new message to message verticle
			eBus.send("mv_Address", "from dummy");
		});

		eBus.send("mv_Address", "hello!");

		// periodic task, run every 30000 ms (30 sec)
		vertx.setPeriodic(30000, h -> {
			System.out.println("dummy dummy " + new Date());
		});

	}
}
