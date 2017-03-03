package com.rey.bean;

import java.util.concurrent.atomic.AtomicInteger;

import io.vertx.core.json.JsonObject;

/**
 * 
 * example bean from Some Rest with Vert.x by cescoffier
 * http://vertx.io/blog/some-rest-with-vert-x/
 *
 */
public class Whisky {
	private static final AtomicInteger COUNTER = new AtomicInteger();

	private final int id;

	private String name;

	private String origin;

	public Whisky(String name, String origin) {
		this.id = COUNTER.getAndIncrement();
		this.name = name;
		this.origin = origin;
	}

	public Whisky() {
		this.id = COUNTER.getAndIncrement();
	}

	public Whisky(int id, String name, String origin) {
		this.id = id;
		this.name = name;
		this.origin = origin;
	}

	public static Whisky fromJson(JsonObject json) {
		
// json.getValue() is case-sensitive
		Whisky obj = new Whisky((int) json.getValue("ID"), (String) json.getValue("NAME"),
				(String) json.getValue("ORIGIN"));

		return obj;
	}

	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
