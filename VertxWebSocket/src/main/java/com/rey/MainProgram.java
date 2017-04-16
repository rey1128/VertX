package com.rey;

import io.vertx.core.Vertx;

public class MainProgram {

	public static void main(String[] args) {
		System.out.println("start...");
		Vertx vertx=Vertx.vertx();
//		vertx.deployVerticle(AuctionVerticle.class.getName());
		vertx.deployVerticle(WebSocketVerticle.class.getName());
		vertx.deployVerticle(WebVerticle.class.getName());
//		vertx.deployVerticle(WebsocketsClient.class.getName());
		System.out.println("deploy successfully");
	}
}
