package com.web.vertx_stock_broker.config;

import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class BrokerConfig {

  int serverPort;
  String version;

  public BrokerConfig(int serverPort, String version) {

    this.serverPort = serverPort;
    this.version = version;
  }

  public int getServerPort() {
    return serverPort;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public static BrokerConfig from(final JsonObject config){
    final Integer serverPort = config.getInteger(ConfigLoader.SERVER_PORT);
    if(Objects.isNull(serverPort)) {
      throw new RuntimeException(ConfigLoader.SERVER_PORT + " not configured.");
    }
    final String version = config.getString("version");
    if(Objects.isNull(version)) {
      throw new RuntimeException("Version is " + " not configured in config file!");
    }
      return new BrokerConfig(serverPort, version);
    }

}
