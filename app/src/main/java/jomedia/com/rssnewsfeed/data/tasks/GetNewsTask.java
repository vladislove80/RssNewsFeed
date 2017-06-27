package jomedia.com.rssnewsfeed.data.tasks;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jomedia.com.rssnewsfeed.data.api.RestApi;
import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.models.NewsFeedResponse;
import jomedia.com.rssnewsfeed.data.models.Item;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.data.models.RssModel;
import jomedia.com.rssnewsfeed.data.db.DatabaseSource;
import jomedia.com.rssnewsfeed.utils.Utils;

public class GetNewsTask implements Runnable {

    @NonNull
    private final DatabaseSource localDataSource;
    @NonNull
    private final RestApi restApi;
    @NonNull
    private final Handler mainHandler;
    @NonNull
    private final NewsCallback<NewsFeedResponse> callback;
    private boolean isOffline;
    String link;

    public GetNewsTask(@NonNull DatabaseSource localDataSource,
                       @NonNull RestApi restApi,
                       @NonNull Handler mainHandler,
                       @NonNull NewsCallback<NewsFeedResponse> callback,
                       @NonNull String link) {
        this.localDataSource = localDataSource;
        this.restApi = restApi;
        this.mainHandler = mainHandler;
        this.callback = callback;
        this.link = link;
        Log.v(Utils.LOG, "GetNewsTask: ");
    }

    @Override
    public void run() {
        Log.v(Utils.LOG, "GetNewsTask -> run");
        List<NewsFeedItemModel> newsFeedItemModels = new ArrayList<>();
        List<Item> items = getItems();
        if (!items.isEmpty()) {
            newsFeedItemModels = Utils.getNewsFeedItems(items);
        }
        mainHandler.post(new CallbackToUI(new NewsFeedResponse(newsFeedItemModels, isOffline)));
    }

    private List<Item> getItems() {
        isOffline = true;
        List<Item> remoteItems = getRemoteNews();
        List<Item> localItems = localDataSource.getAllItems();
        if (remoteItems == null) {
            if (localItems == null) {
                localItems = Collections.emptyList();
            }
            return localItems;
        } else {
            isOffline = false;
            saveNews(remoteItems);
            return remoteItems;
        }
    }

    private void saveNews(List<Item> items) {
        if (items != null) {
            localDataSource.saveNews(items);
        }
    }

    private List<Item> getRemoteNews() {
        List<Item> items = null;
        try {
            RssModel rssModel = restApi.getRssData(link).execute().body();
            if (rssModel != null) {
                items = rssModel.getChannel().getItems();
            }
        } catch (IOException e) {
            e.printStackTrace();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(new IOException("Response not converted to list of post"));
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(new IOException("Smthn wrong with XML response.."));
                }
            });
        }
        return items;
    }

    private class CallbackToUI implements Runnable {
        private final NewsFeedResponse response;

        public CallbackToUI(NewsFeedResponse response) {
            this.response = response;
        }

        @Override
        public void run() {
            callback.onEmit(response);
            callback.onCompleted();
        }
    }
}
