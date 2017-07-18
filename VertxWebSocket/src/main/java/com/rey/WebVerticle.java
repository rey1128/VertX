package com.rey;

import com.rey.utils.AppUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class WebVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		router.route("/index").handler(this::demo);
//		router.route("/web").handler(this::websocketPage);
//		router.route("/ws_adress").handler(this::websocketAddress);
//		router.route("/check_update").handler(this::checkUpdate);
		router.route("/web/*").handler(StaticHandler.create("web"));

//		router.get("/getAll").handler(this::getAll);
		HttpServerOptions options = new HttpServerOptions();
		options.setSsl(true);

		options.setTrustOptions(AppUtils.certificate.trustOptions());
		options.setKeyCertOptions(AppUtils.certificate.keyCertOptions());

		vertx.createHttpServer(options).requestHandler(router::accept).listen(8181);
		
		EventBus eBus=vertx.eventBus();
		eBus.consumer("demo_ebus",handler->{
			System.out.println("at server: "+handler.body());
			eBus.send("demo_reg", Json.encode("from server"));
		});

		// vertx.createHttpServer(options).requestHandler(router::accept).listen(9191);
		
		
		SockJSHandler sockJSHandler=SockJSHandler.create(vertx);
	
		sockJSHandler.socketHandler(handler->{
handler.remoteAddress().port();
			System.out.println("in socket handler");
			handler.handler(data->{
				System.out.println(data);
				handler.write("from server");
			});
		});
		
		router.route("/sockjs/*").handler(sockJSHandler);
		


	}

	public void demo(RoutingContext context) {
		System.out.println("indexPage()");
//		context.response().sendFile("web/demo.html");
		context.response().sendFile("web/index.html");
	}

	public void getAll(RoutingContext context) {
		System.out.println("getAll()");
		JsonObject responseJson=new JsonObject();
		responseJson.put("data", AppUtils.userBeans);
		responseJson.put("number", AppUtils.userBeans.size());
		context.response().end(Json.encodePrettily(responseJson));
	}

	// WEB SOCKET
	public void websocketPage(RoutingContext context) {
		System.out.println("websocketPage()");
		context.response().sendFile("web/ws.html");
	}

	public void websocketAddress(RoutingContext context) {
		System.out.println("websocketAddress()");
		context.response().end(AppUtils.WS_ADDRESS);
	}

	public void checkUpdate(RoutingContext context) {
		System.out.println(AppUtils.Version);
		context.response().end(AppUtils.Version + "");
	}

}
