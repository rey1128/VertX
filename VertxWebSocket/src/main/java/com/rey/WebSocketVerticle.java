package com.rey;

import java.util.HashMap;
import java.util.Map;

import com.rey.utils.AppUtils;
import com.rey.utils.MessageBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;

public class WebSocketVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		HttpServerOptions options=new HttpServerOptions();
		options.setSsl(true);
		
//		options.setKeyStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("vertex"));
//		options.setTrustStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("vertex"));
		
//		options.setClientAuth(ClientAuth.REQUEST);
		
		options.setKeyCertOptions(AppUtils.certificate.keyCertOptions());
		options.setTrustOptions(AppUtils.certificate.trustOptions());
//		options.setWebsocketSubProtocols("base46");
//		options.addEnabledSecureTransportProtocol("TLSv1.2,TLSv1.1,TLSv1");
		
		
		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
			
			public void handle(final ServerWebSocket ws) {
				System.out.println("websocket is established");
				// System.out.println(ws.path());

				ws.handler(new Handler<Buffer>() {
					public void handle(Buffer data) {
						String rec = data.toString();
						System.out.println("at server: " + rec);
						Map<String, String> parameters = Json.decodeValue(rec, HashMap.class);

						String header = parameters.get(AppUtils.HEADER);

						switch (header) {
						case "getAll":
							System.out.println("socket server get all");
							AppUtils.Version=AppUtils.VERSION_RANDOM.nextInt();
							MessageBean mBean = new MessageBean("getAll", AppUtils.userBeans);

							ws.writeFinalTextFrame(Json.encode(mBean));
							break;
						case "myapp":
							System.out.println("socket server myapp");
							MessageBean mBean1 = new MessageBean("myapp", Json.encode(parameters.get("msg")));
							ws.writeFinalTextFrame(Json.encode(mBean1));
							break;
						default:
							System.err.println("header not supported");
							break;
						}
						// ws.reject();
					}
				});

			}
		}).listen(8181);
		// .requestHandler(new Handler<HttpServerRequest>() {
		// public void handle(HttpServerRequest req) {
		// if (req.path().equals("/"))
		// req.response().sendFile("web/ws.html"); // Serve the html
		// }
		// })
	}

}
