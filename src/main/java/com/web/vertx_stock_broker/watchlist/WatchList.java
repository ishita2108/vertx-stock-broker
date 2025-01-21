package com.web.vertx_stock_broker.watchlist;

import com.web.vertx_stock_broker.assets.Asset;
import io.vertx.core.json.JsonObject;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


public class WatchList {


  private List<Asset> assets;

  public WatchList(List<Asset> assets) {
    this.assets = assets;
  }

  public WatchList() {
  }

  public List<Asset> getAssets() {
    return assets;
  }

  public void setAssets(List<Asset> assets) {
    this.assets = assets;
  }

  public JsonObject toJsonObject(){
    return JsonObject.mapFrom(this);
  }
}
