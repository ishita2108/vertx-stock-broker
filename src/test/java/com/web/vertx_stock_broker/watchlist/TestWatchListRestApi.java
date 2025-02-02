package com.web.vertx_stock_broker.watchlist;

import com.web.vertx_stock_broker.AbstractRestApiTest;
import com.web.vertx_stock_broker.MainVerticle;
import com.web.vertx_stock_broker.assets.Asset;
import com.web.vertx_stock_broker.assets.TestAssetsRestApi;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

@ExtendWith(VertxExtension.class)
public class TestWatchListRestApi extends AbstractRestApiTest {

  private static final Logger LOG = LoggerFactory.getLogger(TestWatchListRestApi.class);

  @Test
  void add_and_returns_watchlist_for_account(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(TEST_SERVER_PORT));
    var accountId = UUID.randomUUID();
    client.put("/account/watchlist/" + accountId.toString())
      .sendJsonObject(getBody())
      .onComplete(testContext.succeeding(response -> {
      var json = response.bodyAsJsonObject();
      LOG.info("Response PUT: {}", json);
      Assertions.assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
      Assertions.assertEquals(200, response.statusCode());
    })).compose(next ->  {
      client.get("/account/watchlist/" + accountId.toString())
        .send()
        .onComplete(testContext.succeeding(response ->{
          var json = response.bodyAsJsonObject();
          LOG.info("Response GET : {} ", json);
          Assertions.assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
          Assertions.assertEquals(200, response.statusCode());
          testContext.completeNow();
        }));
        return Future.succeededFuture();
    });
  }

  private static JsonObject getBody() {
    return new WatchList(Arrays.asList(
      new Asset("AMZN"),
      new Asset("TSLA"))).toJsonObject();
  }

  @Test
  void add_and_delete_watchlist_for_account(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(TEST_SERVER_PORT));
    var accountId = UUID.randomUUID();
    client.put("/account/watchlist/" + accountId.toString())
      .sendJsonObject(getBody())
      .onComplete(testContext.succeeding(response -> {
        var json = response.bodyAsJsonObject();
        LOG.info("Response PUT: {}", json);
        Assertions.assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
        Assertions.assertEquals(200, response.statusCode());
      })).compose(next -> {
        client.delete("/account/watchlist/" + accountId.toString())
          .send()
          .onComplete(testContext.succeeding(response -> {
            var json = response.bodyAsJsonObject();
            LOG.info("Response DELETE : {} ", json);
            Assertions.assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
            Assertions.assertEquals(200, response.statusCode());
            testContext.completeNow();
          }));
        return Future.succeededFuture();
      });

  }
}
