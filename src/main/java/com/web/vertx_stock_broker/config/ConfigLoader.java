package com.web.vertx_stock_broker.config;

import com.web.vertx_stock_broker.assets.AssetsRestApi;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ConfigLoader {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);
  public static final String SERVER_PORT = "SERVER_PORT";
  static final List<String> EXPOSED_ENVIRONMENT_VARIABLES = Arrays.asList(SERVER_PORT);
  static final String CONFIG_FILE = "application.yml";

  public static Future<BrokerConfig> load(Vertx vertx){

    final var exposedKeys = new JsonArray();
    EXPOSED_ENVIRONMENT_VARIABLES.forEach(exposedKeys::add);
    LOG.debug("Fetch Configuration from {} ", exposedKeys.encode());

    //get environment variables from config
    var envStore = new ConfigStoreOptions()
      .setType("env")
      .setConfig(new JsonObject().put("keys", exposedKeys));

    //from system
    var propertyStore = new ConfigStoreOptions()
      .setType("sys")
      .setConfig(new JsonObject().put("cache", false));

    var yamlStrore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject().put("path", CONFIG_FILE));

    var retrival = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions()
        //order defines overload rules
        .addStore(yamlStrore)
        .addStore(propertyStore)
        .addStore(envStore)
        );
    return retrival.getConfig().map(BrokerConfig::from);
  }
}
