package jomedia.com.rssnewsfeed.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.models.DataResponse;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;

public interface NewsRepository {
    void getNewsItems(@NonNull NewsCallback<DataResponse> callback, @NonNull String link);
}
