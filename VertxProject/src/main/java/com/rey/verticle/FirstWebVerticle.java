package com.rey.verticle;

import java.util.LinkedHashMap;
import java.util.Map;

import com.rey.bean.Whisky;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class FirstWebVerticle extends AbstractVerticle {

	private Map<Integer, Whisky> products = new LinkedHashMap<>();

	public void initProducts() {
		System.out.println("init product...");
		Whisky w1 = new Whisky("JackDaniel", "US");
		Whisky w2 = new Whisky("JimBean", "US");

		products.put(w1.getId(), w1);
		products.put(w2.getId(), w2);

	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {

		super.start(startFuture);

		initProducts();
		Router router = Router.router(vertx);
		
		router.route("/").handler(context -> {
			HttpServerResponse response = context.response();
			response.putHeader("content-type", "text/html").end("Hello Web-Vert.X");

		});

		// for url-pattern /web, show the index.html under sources/web folder
		router.route("/web/*").handler(StaticHandler.create("web"));
		
		// create body handle for whisky example
		router.route("/api/whiskies/*").handler(BodyHandler.create());
		
		// get all
		router.get("/api/whiskies").handler(this::getAll);
		
		// create
		router.post("/api/whiskies").handler(this::addOne);
		
		// update
		router.put("/api/whiskies/:id").handler(this::updateOne);

		// delete
		router.route("/api/whiskies/:id").handler(this::deleteOne);

		vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8082),
				result -> {
					if (result.succeeded()) {
						System.out.println("deploy success");

					} else {
						startFuture.fail(result.cause());
					}
				});

	}

	public void getAll(RoutingContext context) {
		System.out.println("get all whiskies");
		context.response().putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(products.values()));
	}

	public void addOne(RoutingContext context) {
		System.out.println("add one whisky");
		Whisky w = Json.decodeValue(context.getBodyAsString(), Whisky.class);
		products.put(w.getId(), w);
		context.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(w));

	}

	public void deleteOne(RoutingContext context) {

		String idString = context.request().getParam("id");
		System.out.println("delete one whisky " + idString);
		if (null == idString) {
			context.response().setStatusCode(400).end();
		} else {
			int id = Integer.parseInt(idString);
			products.remove(id);
//			context.response().setStatusCode(204).end();
			getAll(context);
		}
	}

	public void updateOne(RoutingContext context) {
		String idString = context.request().getParam("id");
		System.out.println("update one whisky " + idString);
		if (null == idString) {
			context.response().setStatusCode(400).end();
		} else {
			int id = Integer.parseInt(idString);
			Whisky w = Json.decodeValue(context.getBodyAsString(), Whisky.class);
			products.put(id, w);
			getAll(context);
		}
	}
}
