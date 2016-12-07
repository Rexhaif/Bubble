package ru.rudn.bubble;

import com.google.common.hash.Hashing;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by Rexhaif on 07.12.2016.
 */
public class HttpVerticle extends AbstractVerticle {
    HttpServer server;
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        server = vertx.createHttpServer(
                new HttpServerOptions().setPort(10003).setTcpNoDelay(true)
        );
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post().handler(routingContext -> {
            JsonObject response = new JsonObject();
            response.put(
                    "hash",
                    Hashing
                            .murmur3_128()
                            .hashBytes(routingContext.getBody().getBytes())
                    .toString()
            );
            routingContext.response().setChunked(true).end(response.encodePrettily());
        });
        router.get().handler(routingContext -> {
           routingContext.response().setChunked(true).write("loli").end();
        });
        server.requestHandler(router::accept).listen();
        startFuture.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        server.close();
    }
}
