package com.rey.verticle;

import java.util.Date;

import io.vertx.core.AbstractVerticle;

public class WorkerVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		System.out.println("WorkerVerticle: start at " + new Date());

		try {
			Thread.sleep(3 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("WorkerVerticle: wake up after sleep " + new Date());

	}

}
