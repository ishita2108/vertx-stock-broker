package com.web.vertx_stock_broker.quotes;

import com.web.vertx_stock_broker.assets.Asset;
import com.web.vertx_stock_broker.assets.AssetsRestApi;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Optional;


public class GetQuotesHandler implements Handler<RoutingContext> {
  private static final Logger LOG = LoggerFactory.getLogger(GetQuotesHandler.class);
  private final Map<String, Quote> cachedQuotes;

  public GetQuotesHandler(Map<String, Quote> cachedQuotes) {
    this.cachedQuotes = cachedQuotes;
  }

  @Override
  public void handle(RoutingContext context) {
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
    }
    }

