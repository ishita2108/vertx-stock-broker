package com.web.vertx_stock_broker;

import com.web.vertx_stock_broker.quotes.QuotesRestApi;
import com.web.vertx_stock_broker.watchlist.WatchListRestApi;
import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.web.vertx_stock_broker.assets.*;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final int PORT = 8888;

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.exceptionHandler( error ->LOG.error("Unhandled: {} ", error));
    vertx.deployVerticle(new MainVerticle())
      .onFailure(err ->LOG.error("Failed to deploy: {} ", err))
      .onSuccess(id ->  LOG.info("Deployed Main Verticle {} ", MainVerticle.class.getName()));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(RestApiVerticle.class.getName(), new DeploymentOptions().setInstances(getProcessors()))
      .onFailure(startPromise::fail).onSuccess(id -> {
      LOG.info("Deployed {} with id {} ", RestApiVerticle.class.getName(), id);
      startPromise.complete();
    });
  }

  private static int getProcessors() {
    return Math.max(1,Runtime.getRuntime().availableProcessors()/2);
  }


}
