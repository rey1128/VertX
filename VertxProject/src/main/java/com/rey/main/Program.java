package com.rey.main;

import com.rey.verticle.FirstVerticle;
import com.rey.verticle.FirstWebVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Program {

	public static void main(String[] args) {
		
		System.out.println("Starting Project...");
		
		// set config
		DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 9999));
		
		// deploy service
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(FirstVerticle.class.getName());
		vertx.deployVerticle(FirstWebVerticle.class.getName(), options);

	}

}
