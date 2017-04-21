package com.rey;

import com.rey.utils.AppUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class WebVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);
		router.route("/").handler(this::indexPage);
		router.route("/web").handler(this::websocketPage);
		router.route("/ws_adress").handler(this::websocketAddress);
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

	}

	public void indexPage(RoutingContext context) {

		context.response().end("this is index page");
	}

	public void websocketPage(RoutingContext context) {
		context.response().sendFile("web/ws.html");
	}
	public void websocketAddress(RoutingContext context) {
		
		context.response().end(AppUtils.WS_ADDRESS);
	}
}
