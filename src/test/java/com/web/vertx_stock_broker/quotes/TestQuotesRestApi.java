package com.web.vertx_stock_broker.quotes;

import com.web.vertx_stock_broker.MainVerticle;
import io.vertx.core.Vertx;
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

@ExtendWith(VertxExtension.class)
public class TestQuotesRestApi {

  private static final Logger LOG = LoggerFactory.getLogger(TestQuotesRestApi.class);

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle()).onComplete(testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void
  returns_quotes_for_asset(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    client.get("/quotes/AMZN").send().onComplete(testContext.succeeding(response -> {
      var json = response.bodyAsJsonObject();
      LOG.info("Response: {}", json);
      Assertions.assertEquals("{\"name\":\"AMZN\"}", json.getJsonObject("asset").encode());
      Assertions.assertEquals(200, response.statusCode());
      testContext.completeNow();

    }));
  }

  @Test
  void
  returns_not_found_for_unknown_asset(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    client.get("/quotes/UNKNOWN").send().onComplete(testContext.succeeding(response -> {
      var json = response.bodyAsJsonObject();
      LOG.info("Response: {}", json);
      Assertions.assertEquals("{\"message\":\"Quote for Asset UNKNOWN not found!\",\"path \":\"/quotes/UNKNOWN\"}", json.encode());
      Assertions.assertEquals(404, response.statusCode());
      testContext.completeNow();

    }));
  }
}
