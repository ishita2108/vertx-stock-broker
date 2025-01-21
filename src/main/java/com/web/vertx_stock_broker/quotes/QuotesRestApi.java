package com.web.vertx_stock_broker.quotes;

import com.web.vertx_stock_broker.assets.Asset;
import com.web.vertx_stock_broker.assets.AssetsRestApi;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.web.vertx_stock_broker.quotes.Quote.*;

public class QuotesRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(QuotesRestApi.class);

  public static  void  attach(Router parent){

    final Map<String, Quote> cachedQuotes = new HashMap<>();
    AssetsRestApi.ASSETS.forEach(symbol ->{
      cachedQuotes.put(symbol, initRandomQuote(symbol));
    });

    parent.get("/quotes/:asset").handler(context ->{
      final String assetParam = context.pathParam("asset");
      LOG.debug("Asset Parameter {} ", assetParam);
      var maybeQuote = Optional.ofNullable(cachedQuotes.get(assetParam));
      if(maybeQuote.isEmpty()){
        context.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject().put("message", "Quote for Asset " + assetParam + " not found!")
          .put("path ", context.normalizedPath()).toBuffer());
      }
      JsonObject response = maybeQuote.get().toJsonObject();
      LOG.info("Path {} responds with {} ", context.normalizedPath(), response.encode());
      context.response().end(response.toBuffer());
    });
  }

  private static Quote initRandomQuote(final String assetParam) {
    return new Quote(new Asset(assetParam),randomValue(),randomValue(),randomValue(),randomValue());
  }

  private static BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
  }
}
