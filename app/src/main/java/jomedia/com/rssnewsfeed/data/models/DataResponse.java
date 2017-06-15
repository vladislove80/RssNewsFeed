package jomedia.com.rssnewsfeed.data.models;

import android.support.annotation.Nullable;

import java.util.List;

public class DataResponse {
    @Nullable
    private List<NewsFeedItemModel> newsFeedItemModels;
    private boolean isOffline;

    public DataResponse(List<NewsFeedItemModel> newsFeedItemModels, boolean isOffline) {
        this.newsFeedItemModels = newsFeedItemModels;
        this.isOffline = isOffline;
    }

    @Nullable
    public List<NewsFeedItemModel> getNewsFeedItemModels() {
        return newsFeedItemModels;
    }

    public boolean isOffline() {
        return isOffline;
    }
}
