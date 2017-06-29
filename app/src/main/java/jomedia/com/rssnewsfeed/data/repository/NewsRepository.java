package jomedia.com.rssnewsfeed.data.repository;

import android.support.annotation.NonNull;

import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.models.NewsFeedResponse;

public interface NewsRepository {
    void getNewsItems(@NonNull NewsCallback<NewsFeedResponse> callback, @NonNull String link, @NonNull String category);
}
