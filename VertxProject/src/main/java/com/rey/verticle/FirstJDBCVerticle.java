package com.rey.verticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rey.bean.Whisky;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class FirstJDBCVerticle extends AbstractVerticle {

	JDBCClient jdbc;
	Router router = Router.router(vertx);

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// TODO Auto-generated method stub
		super.start(startFuture);

		jdbc = JDBCClient.createShared(vertx, config(), "My-Whisky-Collection");

		startBackend(
				(connection) -> createSomeData(connection,
						(nothing) -> startWebApp((http) -> completeStartup(http, startFuture)), startFuture),
				startFuture);
		
		vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8082),
				result -> {
					if (result.succeeded()) {
						System.out.println("deploy success");

					} else {
						startFuture.fail(result.cause());
					}
				});


	}

	public void startBackend(Handler<AsyncResult<SQLConnection>> next, Future<Void> fut) {
		System.out.println("get connnection in startBackend");
		jdbc.getConnection(ar -> {
			if (ar.failed()) {
				fut.fail(ar.cause());
			} else {
				next.handle(Future.succeededFuture(ar.result()));
			}
		});
	}

	public void createSomeData(AsyncResult<SQLConnection> result, Handler<AsyncResult<Void>> next, Future<Void> fut) {
		System.out.println("init tables in database");
		if (result.failed()) {
			fut.fail(result.cause());
		} else {
			SQLConnection connection = result.result();
			connection.execute(
					"CREATE TABLE IF NOT EXISTS Whisky(id INTEGER Identity,name varchar(255),origin varchar(255))",
					ar -> {
						if (ar.failed()) {
							fut.fail(ar.cause());
							connection.close();
							return;
						}

						connection.query("SELECT * FROM Whisky", select -> {
							if (select.failed()) {
								fut.fail(select.cause());
								connection.close();
								return;
							}
							if (select.result().getNumRows() == 0) {
								insert(new Whisky("Bowmore", "Scotland"), connection,
										(v) -> insert(new Whisky("Talisker", "Scotland"), connection, (r) -> {
											next.handle(Future.<Void>succeededFuture());
											connection.close();
										}));

							} else {
								next.handle(Future.<Void>succeededFuture());
								connection.close();
							}
						});
					});
		}

	}

	private void insert(Whisky whisky, SQLConnection connection, Handler<AsyncResult<Whisky>> next) {
		System.out.println("insert record in tables");
	
	//	String sql = "INSERT INTO Whisky ( name, origin) VALUES  (?, ?)";
		String sql="INSERT INTO Whisky VALUES (?, ?, ?)";
		
		
		System.out.println(whisky.getName()+"\t"+whisky.getOrigin());
		
		connection.updateWithParams(sql, new JsonArray().add(whisky.getId()).add(whisky.getName()).add(whisky.getOrigin()), (ar) -> {
			if (ar.failed()) {
				ar.cause().printStackTrace();
				next.handle(Future.failedFuture(ar.cause()));
				return;
			}
			
			System.out.println("insert finished");
			UpdateResult result = ar.result();
			System.out.println("keys: "+result.getKeys());
			System.out.println("updated "+result.getUpdated());
			// Build a new whisky instance with the generated id.
			 Whisky w = new Whisky(result.getKeys().getInteger(0), whisky.getName(), whisky.getOrigin());
             next.handle(Future.succeededFuture(w));
             connection.close();
		});
	}

	public void getAll(RoutingContext context) {
		System.out.println("get all");
		jdbc.getConnection(ar -> {
			SQLConnection connection = ar.result();
			connection.query("SELECT * FROM Whisky", result -> {
				// List<Whisky> whiskies =
				// result.result().getRows().stream().map(Whisky::new).collect(Collectors.toList());
				
				List<JsonObject> jsonObjects = result.result().getRows().stream().collect(Collectors.toList());
				System.out.println("jsonObjects size "+result.result().getNumRows());
				List<Whisky> whiskies = new ArrayList<>();
				for (JsonObject object : jsonObjects) {
					whiskies.add(Whisky.fromJson(object));
				}
				context.response().putHeader("content-type", "application/json; charset=utf-8")
						.end(Json.encodePrettily(whiskies));
				connection.close();

			});
		});
	}

	private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
		router.get("/api/whiskies").handler(this::getAll);
		router.post("/api/whiskies").handler(this::addOne);
		router.put("/api/whiskies/:id").handler(this::updateOne);
		router.delete("/api/whiskies/:id").handler(this::deleteOne);
		next.handle(Future.succeededFuture());
	}

	public void addOne(RoutingContext context) {
		System.out.println("add one whisky");
		// TODO

	}

	public void deleteOne(RoutingContext context) {

		String idString = context.request().getParam("id");
		System.out.println("delete one whisky " + idString);
		// TODO
	}

	public void updateOne(RoutingContext context) {
		String idString = context.request().getParam("id");
		System.out.println("update one whisky " + idString);
		// TODO
	}

	private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
		if (http.succeeded()) {
			System.out.println("Application started");
		} else {
			fut.fail(http.cause());
		}
	}

}
