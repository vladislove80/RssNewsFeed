package jomedia.com.rssnewsfeed.data.api;

import jomedia.com.rssnewsfeed.data.models.RssModel;
import retrofit2.http.GET;
import rx.Observable;

public interface RestApi {
    @GET("cmlink/rss-topstories")
    Observable<RssModel> getRssData();
}
