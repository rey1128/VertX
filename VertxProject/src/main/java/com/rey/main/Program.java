package com.rey.main;

import com.rey.verticle.DummyVerticle;
import com.rey.verticle.FirstWebVerticle;
import com.rey.verticle.MessageVerticle;
import com.rey.verticle.WorkerVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

public class Program {

	public static void main(String[] args) {

		System.out.println("Starting Project...");

		// worker verticle is for long-running task
		// set max execute time for worker: 4 min
		// default value is 60000000000 ns (60 seconds)
		// if not set new max execute time, the thread checker will print
		// exceptions
		VertxOptions vertxOptions = new VertxOptions();
		long maxWorkerExecuteTime = 4 * 60000000000L;
		vertxOptions.setMaxWorkerExecuteTime(maxWorkerExecuteTime);

		// event loop verticle should never be blocking
		// default max execute time for event loop verticle is 2000000000 ns (2 seconds)
		// vertxOptions.setMaxEventLoopExecuteTime(maxEventLoopExecuteTime)

		Vertx vertx = Vertx.vertx(vertxOptions);

		// deploy verticles
		vertx.deployVerticle(FirstWebVerticle.class.getName(),
				new DeploymentOptions().setConfig(new JsonObject().put("http.port", 9999)));
		
		vertx.deployVerticle(DummyVerticle.class.getName());
		vertx.deployVerticle(MessageVerticle.class.getName());

		// deploy worker
		vertx.deployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true));

		// DeploymentOptions optionsDB = new DeploymentOptions().setConfig(new
		// JsonObject().put("http.port", 9999)
		// .put("url",
		// "jdbc:hsqldb:file:audit-db;shutdown=true").put("driverclass",
		// "org.hsqldb.jdbcDriver"));
		//
		// vertx.deployVerticle(FirstJDBCVerticle.class.getName(),optionsDB);

		// JDBC Deploy options
		// {
		// "url": "jdbc:hsqldb:file:audit-db;shutdown=true",
		// "driverclass" : "org.hsqldb.jdbcDriver"
		// }

	}

}
