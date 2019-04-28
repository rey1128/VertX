package com.rey.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

public class RedisSubscriber extends AbstractVerticle {
	private static Logger logger = LoggerFactory.getLogger(RedisSubscriber.class.getName());

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);

		Redis.createClient(vertx, new RedisOptions()).connect(onConnect -> {
			Redis redis = onConnect.result();
			redis.send(Request.cmd(Command.SUBSCRIBE).arg(RedisPublisher.CHANNEL), onSend -> {
				if (onSend.succeeded()) {
					logger.info("sending cmd successfully");
				} else {
					logger.error("error with sending cmd");
				}
			});
		}).handler(res -> {
			logger.info("==============\nreading msg from " + RedisPublisher.CHANNEL + "\n==============");

			logger.info("response type: " + res.type());
			res.forEach(eachMsg -> {
				logger.info(String.format("msg content %s, type is %s", eachMsg, eachMsg.type()));
			});
		});
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(RedisSubscriber.class.getName());
	}
}
