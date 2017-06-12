package jomedia.com.rssnewsfeed.data.api;

import android.support.annotation.NonNull;

import jomedia.com.rssnewsfeed.data.models.RssModel;
import rx.Observable;

public class RestManager {
    private static final int RETRY_COUNT_FOR_REQUEST = 0;

    private RestManager() {}
    @NonNull
    public static Observable<RssModel> getRssModel() {
        return RestFactory.getRestApi()
                .getRssData()
                .retry(RETRY_COUNT_FOR_REQUEST)
                .map(response -> response);
    }
}
