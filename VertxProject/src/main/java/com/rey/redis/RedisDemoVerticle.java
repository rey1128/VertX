package com.rey.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;

public class RedisDemoVerticle extends AbstractVerticle {
	private static Logger logger = LoggerFactory.getLogger(RedisDemoVerticle.class.getName());

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);

		logger.info("RedisDemoVerticle is running");

		Redis.createClient(vertx, new RedisOptions()).connect(res -> {
			if (res.succeeded()) {
				logger.info("connect to redis successfully");

				Redis client = res.result();
				RedisAPI api = RedisAPI.api(client);

				api.get("sao:op:singer", msg -> {
					if (msg.succeeded()) {
						logger.info("get value from redis");
						logger.info(String.format("value is %s, type is %s", msg.result(), msg.result().type()));

					} else {
						logger.error("Failed to get value from redis");
					}
				});
			} else {
				logger.error("Failed to connect to redis");
			}
		});

	}

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(RedisDemoVerticle.class.getName());
	}

}
