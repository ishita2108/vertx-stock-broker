package com.web.vertx_stock_broker;

import com.web.vertx_stock_broker.config.ConfigLoader;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractRestApiTest {
  protected static Integer TEST_SERVER_PORT = 9000;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    System.setProperty(ConfigLoader.SERVER_PORT, String.valueOf(TEST_SERVER_PORT));
    vertx.deployVerticle(new MainVerticle()).onComplete(testContext.succeeding(id -> testContext.completeNow()));
  }
}
