package com.web.vertx_stock_broker.watchlist;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static com.web.vertx_stock_broker.watchlist.WatchListRestApi.getAccountId;

public class DeleteWatchListHandler implements Handler<RoutingContext> {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteWatchListHandler.class);
  private final  HashMap<UUID, WatchList> watchListPerAccount;

  public DeleteWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
    this.watchListPerAccount = watchListPerAccount;
  }

  @Override
  public void handle(RoutingContext context) {
    var accountId =  getAccountId(context);
    WatchList deleted= watchListPerAccount.remove(UUID.fromString(accountId));
    LOG.info("Deleted {}, Remaining {} ", deleted.toJsonObject(),watchListPerAccount.values() );
    context.response().end(deleted.toJsonObject().toBuffer());
  }
}
