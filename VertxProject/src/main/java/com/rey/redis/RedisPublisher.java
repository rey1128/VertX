package com.rey.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

public class RedisPublisher extends AbstractVerticle {
	public static final String CHANNEL = "channel1";
	private static Logger logger = LoggerFactory.getLogger(RedisPublisher.class.getName());

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);
		
		RedisClient.create(vertx).publish(RedisPublisher.CHANNEL, "Hello Redis from RedisClient", hr -> {
			if (hr.succeeded()) {
				logger.info("publish successfully from RedisClient");
			} else {
				logger.error("error with publishing message, " + hr.cause());
			}
		});

		vertx.setPeriodic(3000, per -> {
			Redis.createClient(vertx, new RedisOptions()).connect(hr -> {
				hr.result().send(
						Request.cmd(Command.PUBLISH).arg(RedisPublisher.CHANNEL).arg("Periodic Hello Redis from RedisSendCMD"),
						res -> {
							if (res.succeeded()) {
								logger.info("publish successfully from RedisSendCMD");
							} else {
								logger.error("error with publishing message, " + hr.cause());
							}
						});
			});
		});
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(RedisPublisher.class.getName());
	}
}
