package jomedia.com.rssnewsfeed.rest.api;

import jomedia.com.rssnewsfeed.rest.model.RssModel;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("cmlink/rss-topstories")
    Observable<RssModel> getRssData();
}
