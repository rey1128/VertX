package com.rey;

import io.vertx.core.Vertx;

public class MainProgram {

	public static void main(String[] args) {
		System.out.println("start...");
		// VertxOptions options = new VertxOptions();

		// options.setEventBusOptions(new EventBusOptions().setSsl(true)
		// .setKeyStoreOptions(new
		// JksOptions().setPath("keystore.jks").setPassword("vertex"))
		// .setTrustStoreOptions(new
		// JksOptions().setPath("keystore.jks").setPassword("vertex")));
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(AuctionVerticle.class.getName());
		vertx.deployVerticle(WebVerticle.class.getName());
//		vertx.deployVerticle(WebSocketVerticle.class.getName());

//		vertx.deployVerticle(WebSocketClient.class.getName());
		System.out.println("deploy successfully");
	}
}
