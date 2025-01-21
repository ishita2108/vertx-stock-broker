package com.web.vertx_stock_broker.quotes;

import com.web.vertx_stock_broker.assets.Asset;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;


public class Quote {

  private Asset asset;
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal lastPrice;
  private BigDecimal volume;

  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  public void setBid(BigDecimal bid) {
    this.bid = bid;
  }

  public void setAsk(BigDecimal ask) {
    this.ask = ask;
  }

  public void setLastPrice(BigDecimal lastPrice) {
    this.lastPrice = lastPrice;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public Asset getAsset() {
    return asset;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Builder
  public Quote(Asset asset, BigDecimal bid, BigDecimal ask, BigDecimal lastPrice, BigDecimal volume) {
    this.asset = asset;
    this.bid = bid;
    this.ask = ask;
    this.lastPrice = lastPrice;
    this.volume = volume;
  }

  public JsonObject toJsonObject(){
    return JsonObject.mapFrom(this);
  }
}
