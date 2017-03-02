package com.rey.main;

import com.rey.verticle.FirstVerticle;

import io.vertx.core.Vertx;

public class Program {

	public static void main(String[] args) {
		System.out.println("Hello Gradle");

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(FirstVerticle.class.getName());
		
	}

}
