package com.web.vertx_stock_broker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.web.vertx_stock_broker.assets.*;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.exceptionHandler( error ->{
      LOG.error("Unhandled: {} ", error);
    });
    vertx.deployVerticle(new MainVerticle(), ar ->{
      if (ar.failed()) {
        LOG.error("Failed to deploy: {} ", ar.cause());
        return;
      }
      LOG.info("Deployed Main Verticle {} ", MainVerticle.class.getName());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    final Router restApi = Router.router(vertx);
    AssetsRestApi.attach(restApi);
    vertx.createHttpServer().requestHandler(restApi
//      req -> {
//      req.response()
//        .putHeader("content-type", "text/plain")
//        .end("Hello from Vert.x!");
//    }
    ).exceptionHandler(error ->{
      LOG.error("HTTP Server Error {} ",error);
      })
      .listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        LOG.info("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
