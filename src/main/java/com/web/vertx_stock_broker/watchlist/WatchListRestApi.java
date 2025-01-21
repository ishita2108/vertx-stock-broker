package com.web.vertx_stock_broker.watchlist;

import com.web.vertx_stock_broker.quotes.QuotesRestApi;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class WatchListRestApi {

  private static final Logger LOG = LoggerFactory.getLogger(WatchListRestApi.class);

  public static void attach(Router parent){

    final HashMap<UUID, WatchList> watchListPerAccount = new HashMap<UUID, WatchList>();

    final String path = "/account/watchlist/:accountId";


    parent.get(path).handler(context -> {
      var accountId = context.pathParam("accountId");
      LOG.debug("{} for account {}", context.normalizedPath(), accountId);
     var watchList = Optional.ofNullable(watchListPerAccount.get(UUID.fromString(accountId)));
     if(watchList.isEmpty()){
       context.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject().put("message", "WatchList for account " + accountId + " not found!")
         .put("path :" , context.normalizedPath()).toBuffer());
     }
     context.response().end(watchList.get().toJsonObject().toBuffer());
    });

    parent.put(path).handler(context -> {
      var accountId = context.pathParam("accountId");
      LOG.debug("{} for account {}", context.normalizedPath(), accountId);
      var jsonBody = context.getBodyAsJson();
      var watchListJson = jsonBody.mapTo(WatchList.class);
      watchListPerAccount.put(UUID.fromString(accountId),watchListJson );
      context.response().end(jsonBody.toBuffer());

    });

    parent.delete(path).handler(context -> {

    });
  }
}
