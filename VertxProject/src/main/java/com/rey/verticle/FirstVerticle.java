package com.rey.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class FirstVerticle extends AbstractVerticle{
	
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// TODO Auto-generated method stub
//		super.start(startFuture);
		

		System.out.println(config().getInteger("http.port"));
		vertx.createHttpServer().requestHandler(r->{
			r.response().end("Hello VertX");
			
		}).listen(config().getInteger("http.port",8080), context->{
			if(context.succeeded()) {
				startFuture.complete();
			}else {
				startFuture.fail(context.cause());
			}
		});
	}

}
