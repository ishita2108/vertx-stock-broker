package com.web.vertx_stock_broker.assets;

import lombok.Value;

import java.util.List;

public class Asset {

  private String name = "";

  public Asset(String name) {
    this.name = name;
  }

  public Asset() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
