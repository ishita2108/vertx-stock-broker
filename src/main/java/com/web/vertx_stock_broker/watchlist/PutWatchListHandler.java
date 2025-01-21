package com.web.vertx_stock_broker.watchlist;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

import static com.web.vertx_stock_broker.watchlist.WatchListRestApi.getAccountId;

public class PutWatchListHandler implements Handler<RoutingContext> {

  private static final Logger LOG = LoggerFactory.getLogger(PutWatchListHandler.class);
  private final  HashMap<UUID, WatchList> watchListPerAccount;

  public PutWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
    this.watchListPerAccount = watchListPerAccount;
  }

  @Override
  public void handle(RoutingContext context) {
    var accountId = getAccountId(context);
    var jsonBody = context.getBodyAsJson();
    var watchListJson = jsonBody.mapTo(WatchList.class);
    watchListPerAccount.put(UUID.fromString(accountId),watchListJson );
    context.response().end(jsonBody.toBuffer());
  }
}
