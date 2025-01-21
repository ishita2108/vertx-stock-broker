package com.web.vertx_stock_broker;

import com.web.vertx_stock_broker.assets.AssetsRestApi;
import com.web.vertx_stock_broker.config.BrokerConfig;
import com.web.vertx_stock_broker.config.ConfigLoader;
import com.web.vertx_stock_broker.quotes.QuotesRestApi;
import com.web.vertx_stock_broker.watchlist.WatchListRestApi;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionInfoVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VersionInfoVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(configuration -> {
        LOG.info("Current Application Version is: {}", configuration.getVersion());
        startPromise.complete();
      });
  }
}
