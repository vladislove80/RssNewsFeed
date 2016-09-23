package jomedia.com.rssnewsfeed.rest.api;

import android.support.annotation.NonNull;

import jomedia.com.rssnewsfeed.rest.model.RssModel;
import rx.Observable;

public class ApiManager {
    private static final int RETRY_COUNT_FOR_REQUEST = 0;

    private ApiManager() {}
    @NonNull
    public static Observable<RssModel> getRssModel() {
        return ApiFactory.getApiService()
                .getRssData()
                .retry(RETRY_COUNT_FOR_REQUEST)
                .map(response -> response);
    }
}
