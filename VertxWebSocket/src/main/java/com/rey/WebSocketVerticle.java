package com.rey;

import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;

public class WebSocketVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
			public void handle(final ServerWebSocket ws) {
				if (ws.path().equals("/myapp")) {

					System.out.println("in web socket server");
					ws.handler(new Handler<Buffer>() {
						public void handle(Buffer data) {
							System.out.println("at server: " + data.toString());
							// Echo it back
							String sendBack=data.toString()+" "+new Date();
							ws.writeFinalTextFrame(sendBack); 
											

						}
					});
				} else {
					ws.reject();
				}
			}
		}).listen(8080);
//		.requestHandler(new Handler<HttpServerRequest>() {
//			public void handle(HttpServerRequest req) {
//				if (req.path().equals("/"))
//					req.response().sendFile("web/ws.html"); // Serve the html
//			}
//		})
	}

}
