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
    private final NewsCallback<List<NewsFeedItemModel>> callback;
    private boolean isOffLine = false;

    public GetNewsTask(@NonNull DatabaseSource localDataSource,
                       @NonNull RestApi restApi,
                       @NonNull Handler mainHandler,
                       @NonNull NewsCallback<List<NewsFeedItemModel>> callback) {
        this.localDataSource = localDataSource;
        this.restApi = restApi;
        this.mainHandler = mainHandler;
        this.callback = callback;
        Log.i(Utils.LOG, "GetNewsTask: ");
    }

    @Override
    public void run() {
        Log.i(Utils.LOG, "GetNewsTask -> run");
        List<NewsFeedItemModel> newsFeedItemModels = new ArrayList<>();
        List<Item> items = getItems();
        if (!items.isEmpty()) {
            newsFeedItemModels = Utils.getNewsFeedItems(items);
        }
        mainHandler.post(new CallbackToUI(newsFeedItemModels, isOffLine));
    }

    private List<Item> getItems() {
        List<Item> items = getRemoteNews();

        if (items == null) {
            isOffLine = true;
            items = localDataSource.getAllItems();
        } else {
            saveNews(items);
        }

        return items == null ? Collections.emptyList() : items;
    }

    private void saveNews(List<Item> items) {
        if (items != null) {
            localDataSource.saveNews(items);
        }
    }

    private List<Item> getRemoteNews() {
        List<Item> items = null;
        try {
            RssModel rssModel = restApi.getRssData().execute().body();
            items = rssModel.getChannel().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            mainHandler.post(() -> callback.onError(new IOException("Response not converted to list of post")));
        }
        return items;
    }

    private class CallbackToUI implements Runnable {
        private final List<NewsFeedItemModel> news;
        private final boolean isOffLine;

        public CallbackToUI(List<NewsFeedItemModel> news, boolean isOffLine) {
            this.news = news;
            this.isOffLine = isOffLine;
        }

        @Override
        public void run() {
            Log.i(Utils.LOG, "GetNewsTask -> CallbackToUI -> run");
            callback.onEmit(news);
            callback.onCompleted(isOffLine);
        }
    }
}
